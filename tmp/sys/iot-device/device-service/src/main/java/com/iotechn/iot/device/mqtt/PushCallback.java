package com.iotechn.iot.device.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.iot.commons.entity.NotifyMessage;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.device.Const;
import com.iotechn.iot.device.entity.DeviceDo;
import com.iotechn.iot.device.entity.DeviceParamDo;
import com.iotechn.iot.device.entity.model.IotExchangeModel;
import com.iotechn.iot.device.mapper.DeviceMapper;
import com.iotechn.iot.device.mapper.DeviceParamMapper;
import com.iotechn.iot.device.service.biz.DeviceBizServiceImpl;
import com.iotechn.iot.executor.api.ExecutionOpenService;
import com.iotechn.iot.executor.model.InvokerInfoModel;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PushCallback implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(PushCallback.class);

    public static final String DEVICE_PARAM_PREFIX = "D_PARAM_";

    private volatile boolean connected = false;

    private DeviceBizServiceImpl deviceBizService;
    private DeviceParamMapper deviceParamMapper;
    private StringRedisTemplate stringRedisTemplate;
    private MqttClient mqttClient;
    private ExecutionOpenService executionOpenService;

    public PushCallback(DeviceBizServiceImpl deviceBizService, DeviceParamMapper deviceParamMapper, StringRedisTemplate stringRedisTemplate, MqttClient mqttClient, ExecutionOpenService executionOpenService) {
        this.deviceBizService = deviceBizService;
        this.deviceParamMapper = deviceParamMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.mqttClient = mqttClient;
        this.executionOpenService = executionOpenService;
    }

    public void connectionLost(Throwable cause) {
        if (cause != null) {
            logger.warn("[客户端断开连接]", cause);
        }
        //尝试从新连接

        try {
            mqttClient.connect();
            logger.info("[emqx] 客户端已经稳定连接");
        } catch (Exception e) {
            logger.error("重新连接失败", e);
            connected = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!connected) {
                        try {
                            Thread.sleep(10000);
                            mqttClient.connect();
                            logger.info("[emqx] 客户端已经稳定连接");
                            connected = true;
                        } catch (Exception e1) {
                            logger.error("重新连接失败", e);
                        }
                    }
                }
            }).start();
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void messageArrived(String topic, MqttMessage message) {
        String request = new String(message.getPayload());
        logger.info("[调试]: request=" + request);
        try {
            IotExchangeModel iotExchangeModel = JSONObject.parseObject(request, IotExchangeModel.class);
            if (iotExchangeModel.getType() == IotExchangeModel.TYPE_PARAM) {
                DeviceDo deviceDo = null;
                try {
                    deviceDo = deviceBizService.getDeviceBySK(iotExchangeModel.getSecretKey());
                } catch (ServiceException e) {
                    //不将此异常抛给硬件，直接不回复醒了。
                }
                if (deviceDo != null) {
                    //回传参数包括msg 和 name
                    //1.参数名
                    String name = iotExchangeModel.getName();
                    //2.消息
                    String msg = iotExchangeModel.getMsg();
                    String key = DEVICE_PARAM_PREFIX + name + "_" + deviceDo.getId();

                    String paramCacheJson = stringRedisTemplate.opsForValue().get(key + "_V");
                    DeviceParamDo paramDo = null;
                    if (!StringUtils.isEmpty(paramCacheJson)) {
                        paramDo = JSONObject.parseObject(paramCacheJson, DeviceParamDo.class);
                    } else {
                        List<DeviceParamDo> deviceParamDos = deviceParamMapper
                                .selectList(new EntityWrapper<DeviceParamDo>()
                                        .eq("device_id", deviceDo.getId())
                                        .eq("name", name));
                        if (!CollectionUtils.isEmpty(deviceParamDos) && deviceParamDos.size() == 1) {
                            paramDo = deviceParamDos.get(0);
                        }
                    }

                    if (paramDo != null) {
                        //根据过期时间选择是存到数据库还是存到数据库
                        Integer expire = paramDo.getExpire();
                        if (expire <= Const.PARAM_CACHE_DB_THRESHOLD_VALUE) {
                            stringRedisTemplate.opsForValue().set(key, msg,Const.PARAM_CACHE_DB_THRESHOLD_VALUE, TimeUnit.SECONDS);
                            paramDo.setParamValue(msg);
                            paramDo.setGmtUpdate(new Date());
                            stringRedisTemplate.opsForValue().set(key + "_V",JSONObject.toJSONString(paramDo));
                        } else {
                            //在数据库进行持久化
                            DeviceParamDo deviceParamDo = new DeviceParamDo();
                            deviceParamDo.setParamValue(msg);
                            deviceParamMapper.update(deviceParamDo, new EntityWrapper<DeviceParamDo>()
                                    .eq("device_id", deviceDo.getId())
                                    .eq("name", name));
                        }
                        //推送给网关 ，网关 会通过WS 推送给客户端
                        if (stringRedisTemplate.opsForSet().isMember("GW_PARAM_NOTIFY",iotExchangeModel.getSecretKey())) {
                            //查询redis里面通知列表
                            MqttMessage mqttMessage = new MqttMessage();
                            mqttMessage.setQos(2);
                            NotifyMessage notifyMessage = new NotifyMessage();
                            notifyMessage.setSecretKey(iotExchangeModel.getSecretKey());
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("type", "param");
                            jsonObject.put("name", paramDo.getName());
                            jsonObject.put("value", msg);
                            notifyMessage.setBody(jsonObject.toJSONString());
                            mqttMessage.setPayload(JSONObject.toJSONString(notifyMessage).getBytes());
                            mqttClient.publish(NotifyMessage.NOTIFY_MESSAGE_TOPIC, mqttMessage);
                        }
                    } else {
                        logger.info("[调试 无效参数]:" + request);
                    }
                } else {
                    logger.info("[垃圾信息到达] request=" + request + ";topic=" + topic);
                }
            } else if (iotExchangeModel.getType() == IotExchangeModel.TYPE_INVOKE) {
                DeviceDo deviceDo = null;
                try {
                    deviceDo = deviceBizService.getDeviceBySK(iotExchangeModel.getSecretKey());
                } catch (ServiceException e) {
                    //不将此异常抛给硬件，直接不回复醒了。
                }
                if (deviceDo != null) {
                    String param = iotExchangeModel.getParam().trim();
                    String[] raw = param.split(" ");
                    String[] args = new String[raw.length - 2];
                    if (raw.length > 2) {
                        for (int i = 2; i < raw.length; i++) {
                            args[i - 2] = raw[i];
                        }
                    }
                    try {
                        InvokerInfoModel invokerInfoModel = new InvokerInfoModel();
                        invokerInfoModel.setInvokerId(deviceDo.getName());
                        invokerInfoModel.setInvokerIp(deviceDo.getLastIp());
                        invokerInfoModel.setSecretKey(iotExchangeModel.getSecretKey());
                        Object o = executionOpenService.invokeGroovyMethod(iotExchangeModel.getUuid(), iotExchangeModel.getMethod(), args, invokerInfoModel);
                        mqttClient.publish(iotExchangeModel.getSecretKey()
                                , new MqttMessage(("${is}" + (Const.IGNORE_PARAM_LIST.contains(o.getClass()) ?
                                        o.toString() : JSONObject.toJSONString(o)) + "${ie}").getBytes("utf-8")));
                    } catch (ServiceException e) {
                        mqttClient.publish(iotExchangeModel.getSecretKey()
                                , new MqttMessage(("${is}error!" + e.getMessage() + "${ie}").getBytes("utf-8")));
                    }
                } else {
                    logger.info("[调试 无效的调用]:" + request);
                }
            }

        } catch (Exception e) {
            logger.error("[消息处理] 异常 request=" + request, e);
        }

    }
}