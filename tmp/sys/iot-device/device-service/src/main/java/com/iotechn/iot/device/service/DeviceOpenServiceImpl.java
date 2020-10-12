package com.iotechn.iot.device.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.iot.commons.entity.CommonsPage;
import com.iotechn.iot.commons.entity.IPage;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.device.CodeUtil;
import com.iotechn.iot.device.Const;
import com.iotechn.iot.device.api.DeviceOpenService;
import com.iotechn.iot.device.mqtt.PushCallback;
import com.iotechn.iot.device.entity.DeviceCommandDo;
import com.iotechn.iot.device.entity.DeviceDo;
import com.iotechn.iot.device.entity.DeviceParamDo;
import com.iotechn.iot.device.entity.MqttUserDo;
import com.iotechn.iot.device.entity.exception.DeviceExceptionDefinition;
import com.iotechn.iot.device.entity.exception.DeviceServiceException;
import com.iotechn.iot.device.entity.model.DeviceBasicInfo;
import com.iotechn.iot.device.mapper.DeviceCommandMapper;
import com.iotechn.iot.device.mapper.DeviceMapper;
import com.iotechn.iot.device.mapper.DeviceParamMapper;
import com.iotechn.iot.device.mapper.MqttUserMapper;
import com.iotechn.iot.device.service.biz.DeviceBizServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-15
 * Time: 下午7:56
 */
@Service("deviceOpenService")
@EnableTransactionManagement
public class DeviceOpenServiceImpl implements DeviceOpenService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceOpenServiceImpl.class);

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private DeviceCommandMapper deviceCommandMapper;
    @Autowired
    private DeviceParamMapper deviceParamMapper;
    //    @Autowired
//    private MqttAclMapper mqttAclMapper;
    @Autowired
    private MqttUserMapper mqttUserMapper;
    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DeviceBizServiceImpl deviceBizService;

    private static final int PAGE_SIZE = 10;
    

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceDo createDevice(String name, String password, Long userId) throws ServiceException {
        //1.设备表
        checkDeviceName(name);
        String secretKey = CodeUtil.generateSecretKey();
        String encodedPassword = CodeUtil.getSHA256StrJava(password);
        DeviceDo deviceDo = new DeviceDo();
        deviceDo.setName(name);
        deviceDo.setPassword(encodedPassword);
        deviceDo.setUserId(userId);
        deviceDo.setSecretKey(secretKey);
        deviceDo.setGmtCreate(deviceDo.getGmtUpdate());
        deviceMapper.insert(deviceDo);

        //2.用于队列鉴权
//        MqttAclDo mqttAclDo = new MqttAclDo();
//        mqttAclDo.setAccess(MqttAclDo.ACCESS_PUBSUB);
//        mqttAclDo.setAllow(0);
//        mqttAclDo.setTopic(secretKey);
//        mqttAclDo.setClientid(name);
//        mqttAclDo.setUsername(name);
//        mqttAclDo.setDeviceId(deviceDo.getId());

//        mqttAclMapper.insert(mqttAclDo);
        //mqttAclDo.set

        MqttUserDo mqttUserDo = new MqttUserDo();
        mqttUserDo.setDeviceId(deviceDo.getId());
        mqttUserDo.setCreated(new Date());
        mqttUserDo.setIsSuperuser(0);
        mqttUserDo.setPassword(encodedPassword);
        mqttUserDo.setUsername(name);
        mqttUserMapper.insert(mqttUserDo);
        return deviceDo;
    }

    @Override
    @Transactional
    public Boolean updateDevice(Long deviceId, String secretKey, String password, String title, String description, Long userId) throws ServiceException {
        DeviceDo deviceDo = new DeviceDo();
        if (!StringUtils.isEmpty(password)) {
            deviceDo.setPassword(CodeUtil.getSHA256StrJava(password));
        }
        if (!StringUtils.isEmpty(title)) {
            deviceDo.setTitle(title);
        }
        if (!StringUtils.isEmpty(description)) {
            deviceDo.setDescription(description);
        }
        boolean suc = deviceMapper.update(deviceDo,
                new EntityWrapper<DeviceDo>()
                        .eq("user_id", userId)
                        .eq("secret_key", secretKey)
                        .eq("id", deviceId)) > 0;
        deviceBizService.refreshDeviceCache(deviceDo);
        return suc;
    }

    @Override
    public IPage<DeviceDo> getDevicePage(Integer page, Long userId) throws ServiceException {
        Wrapper<DeviceDo> wrapper = new EntityWrapper<DeviceDo>().eq("user_id", userId);
        List<DeviceDo> deviceDos = deviceMapper.selectPage(new RowBounds((page - 1) * PAGE_SIZE, PAGE_SIZE), wrapper);
        Integer count = deviceMapper.selectCount(wrapper);
        return new CommonsPage<DeviceDo>(deviceDos, page, PAGE_SIZE, count);
    }


    @Override
    public IPage<DeviceCommandDo> getDeviceCommandPage(Integer page, Long deviceId, Long userId) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        Wrapper<DeviceCommandDo> wrapper = new EntityWrapper<DeviceCommandDo>()
                .eq("device_id", deviceId);
        List<DeviceCommandDo> commandDos = deviceCommandMapper.selectPage(
                new RowBounds(PAGE_SIZE * (page - 1), PAGE_SIZE), wrapper);
        Integer count = deviceCommandMapper.selectCount(wrapper);
        return new CommonsPage<DeviceCommandDo>(commandDos, page, PAGE_SIZE, count);
    }

    @Override
    public IPage<DeviceParamDo> getDeviceParamPage(Integer page, Long deviceId, Long userId) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        Wrapper<DeviceParamDo> wrapper = new EntityWrapper<DeviceParamDo>()
                .eq("device_id", deviceId);
        List<DeviceParamDo> paramDos = deviceParamMapper.selectPage(new RowBounds(PAGE_SIZE * (page - 1), PAGE_SIZE), wrapper);
        Integer count = deviceParamMapper.selectCount(wrapper);
        return new CommonsPage<DeviceParamDo>(paramDos, page, PAGE_SIZE, count);
    }

    @Override
    @Transactional
    public Boolean deleteDevice(Long deviceId, String secretKey, Long userId) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        Integer count = deviceMapper.delete(
                new EntityWrapper<DeviceDo>()
                        .eq("user_id", userId)
                        .eq("secret_key", secretKey));
        Integer mqttUserCount = mqttUserMapper.delete(
                new EntityWrapper<MqttUserDo>()
                        .eq("device_id", deviceId));

//        Integer mqttAclCount = mqttAclMapper.delete(
//                new EntityWrapper<MqttAclDo>()
//                        .eq("device_id", deviceId));
        if (count > 0 && mqttUserCount > 0 /*&& mqttAclCount > 0*/) {
            deviceParamMapper.delete(
                    new EntityWrapper<DeviceParamDo>()
                            .eq("device_id", deviceId));
            deviceCommandMapper.delete(
                    new EntityWrapper<DeviceCommandDo>()
                            .eq("device_id", deviceId));
        }
        deviceBizService.deleteCache(secretKey);
        return count > 0;
    }

    @Override
    public DeviceBasicInfo getDeviceBasicInfo(Long userId) throws ServiceException {
        Integer deviceCount = deviceMapper.selectCount(
                new EntityWrapper<DeviceDo>()
                        .eq("user_id", userId));
        Integer deviceOnline = deviceMapper.selectCount(
                new EntityWrapper<DeviceDo>()
                        .eq("user_id", userId)
                        .eq("status", 1));
        DeviceBasicInfo info = new DeviceBasicInfo();
        info.setCount(deviceCount);
        info.setOnlineCount(deviceOnline);
        return info;
    }

    @Override
    public DeviceParamDo addDeviceParam(Long deviceId, String title, String name, Integer expire, Long userId, String description) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        DeviceParamDo paramDo = new DeviceParamDo();
        paramDo.setDeviceId(deviceId);
        paramDo.setTitle(title);
        paramDo.setName(name);
        paramDo.setExpire(expire);
        paramDo.setDescription(description);
        paramDo.setGmtCreate(paramDo.getGmtUpdate());
        deviceParamMapper.insert(paramDo);
        return paramDo;
    }

    @Override
    public DeviceCommandDo addDeviceCommand(Long deviceId, String title, String command, Long userId, String description) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        DeviceCommandDo commandDo = new DeviceCommandDo();
        commandDo.setDeviceId(deviceId);
        commandDo.setTitle(title);
        commandDo.setCommand(command);
        commandDo.setDescription(description);
        commandDo.setGmtCreate(commandDo.getGmtUpdate());
        deviceCommandMapper.insert(commandDo);
        return commandDo;
    }

    @Override
    public Boolean deleteDeviceCommand(Long deviceId, Long commandId, Long userId) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        return deviceCommandMapper.delete(new EntityWrapper<DeviceCommandDo>()
                .eq("id", commandId)
                .eq("device_id", deviceId)) > 0;
    }

    @Override
    public Boolean deleteDeviceParam(Long deviceId, Long commandId, Long userId) throws ServiceException {
        checkDeviceOwner(deviceId, userId);
        return deviceParamMapper.delete(new EntityWrapper<DeviceParamDo>()
                .eq("id", commandId)
                .eq("device_id", deviceId)) > 0;
    }

    /**
     * 注意:如果要加其他关联表，这里一定要加上
     *
     * @param deviceId
     * @param userId
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional
    public Long cloneDevice(Long deviceId, String name, Long userId) throws ServiceException {
        try {
            //1.校验权限
            checkDeviceOwner(deviceId, userId);
            Date now = new Date();
            //2.开始复制device
            DeviceDo deviceDo = deviceMapper.selectById(deviceId);
            deviceDo.setId(null);
            deviceDo.setName(name + "_clone");
            //2.0.校验是否存在重名字
            checkDeviceName(deviceDo.getName());
            deviceDo.setGmtUpdate(now);
            deviceDo.setGmtCreate(now);
            deviceDo.setSecretKey(CodeUtil.generateSecretKey());
            deviceMapper.insert(deviceDo);

            List<DeviceParamDo> paramDos = deviceParamMapper.selectList(
                    new EntityWrapper<DeviceParamDo>()
                            .eq("device_id", deviceId));
            if (!CollectionUtils.isEmpty(paramDos)) {
                for (DeviceParamDo paramDo : paramDos) {
                    paramDo.setId(null);
                    paramDo.setGmtUpdate(now);
                    paramDo.setGmtCreate(now);
                    paramDo.setParamValue(null);
                    deviceParamMapper.insert(paramDo);
                }

            }

            List<DeviceCommandDo> commandDos = deviceCommandMapper.selectList(
                    new EntityWrapper<DeviceCommandDo>()
                            .eq("device_id", deviceId));
            if (!CollectionUtils.isEmpty(commandDos)) {
                for (DeviceCommandDo commandDo : commandDos) {
                    commandDo.setId(null);
                    commandDo.setGmtUpdate(now);
                    commandDo.setGmtCreate(now);
                    deviceCommandMapper.insert(commandDo);
                }
            }

            return deviceDo.getId();
        } catch (Exception e) {
            logger.error("[克隆设备异常]", e);
            throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_UNKNOWN_EXCEPTION);
        }
    }

    /**
     * APP 小程序等移动终端调用以下接口
     **/

    @Override
    public String sendMsgToDevice(String secretKey, String msg, Integer qos) throws ServiceException {
        Integer count = deviceMapper.selectCount(new EntityWrapper<DeviceDo>()
                .eq("secret_key", secretKey));
        if (count > 0) {
            try {
                MqttTopic topic = mqttClient.getTopic(secretKey);
                MqttMessage mqttMessage = new MqttMessage(("${cs}" + msg + "${ce}\r\n").getBytes("utf-8"));
                mqttMessage.setQos(qos);
                topic.publish(mqttMessage);
                return "success";
            } catch (Exception e) {
                logger.error("[发送信息] 异常", e);
                throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_UNKNOWN_EXCEPTION);
            }
        }
        throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_DEVICE_NOT_EXIST);
    }

    @Override
    public String sendCommandToDevice(String secretKey, Long commandId, Integer qos) throws ServiceException {
        List<DeviceCommandDo> commandDos = deviceCommandMapper.selectList(
                new EntityWrapper<DeviceCommandDo>()
                        .eq("secret_key", secretKey));
        if (!CollectionUtils.isEmpty(commandDos)) {
            return sendMsgToDevice(secretKey, commandDos.get(0).getCommand(), qos);
        }
        throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_COMMAND_NOT_EXIST);
    }

    @Override
    public DeviceDo getDeviceBySecretKey(String secretKey) throws ServiceException {
        return deviceBizService.getDeviceBySK(secretKey);
    }

    @Override
    public List<DeviceCommandDo> getDeviceCommandsBySecretKey(String secretKey) throws ServiceException {
        DeviceDo deviceDo = deviceBizService.getDeviceBySK(secretKey);
        List<DeviceCommandDo> commandDos = deviceCommandMapper.selectList(
                new EntityWrapper<DeviceCommandDo>()
                        .eq("device_id", deviceDo.getId()));
        return commandDos;
    }

    @Override
    public List<DeviceParamDo> getDeviceParamsBySecretKey(String secretKey) throws ServiceException {
        DeviceDo deviceDo = deviceBizService.getDeviceBySK(secretKey);
        List<DeviceParamDo> paramDos = deviceParamMapper.selectList(
                new EntityWrapper<DeviceParamDo>()
                        .eq("device_id", deviceDo.getId()));

        if (!CollectionUtils.isEmpty(paramDos)) {
            for (DeviceParamDo paramDo : paramDos) {
                if (paramDo.getExpire() <= Const.PARAM_CACHE_DB_THRESHOLD_VALUE) {
                    //2.从缓存中去拿
                    String paramValue = stringRedisTemplate.opsForValue().get(PushCallback.DEVICE_PARAM_PREFIX
                            + paramDo.getName() + "_" + deviceDo.getId());
                    if (!StringUtils.isEmpty(paramValue))
                        paramDo.setParamValue(paramValue);
                }
            }
        }
        return paramDos;
    }


    /**
     * 检测设备是否属于当前用户
     * //TODO 权限所属校验可以写个切面
     *
     * @param deviceId
     * @param userId
     * @throws ServiceException
     */
    private void checkDeviceOwner(Long deviceId, Long userId) throws ServiceException {
        String value = stringRedisTemplate.opsForValue().get("DEVICE_" + deviceId + "USER_" + userId);
        if ("1".equals(value)) {
            return;
        }
        Integer count = deviceMapper.selectCount(
                new EntityWrapper<DeviceDo>()
                        .eq("id", deviceId)
                        .eq("user_id", userId));
        if (count == 0) {
            throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_NOT_OWNS_YOU);
        } else {
            stringRedisTemplate.opsForValue().set("CACHE_DEVICE_ID_" + deviceId + "_USER_" + userId, "1", 1l, TimeUnit.HOURS);
        }
    }

    private void checkDeviceName(String deviceName) throws ServiceException {
        if (deviceMapper.selectCount(new EntityWrapper<DeviceDo>().eq("name", deviceName)) > 0) {
            throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_HAS_EXIST);
        }
    }


}
