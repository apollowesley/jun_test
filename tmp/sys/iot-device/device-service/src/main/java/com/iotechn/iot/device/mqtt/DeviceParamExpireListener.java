package com.iotechn.iot.device.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.iot.device.entity.DeviceParamDo;
import com.iotechn.iot.device.mapper.DeviceParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-11-06
 * Time: 下午4:35
 */
public class DeviceParamExpireListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(DeviceParamExpireListener.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DeviceParamMapper deviceParamMapper;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String expireKey = message.toString();
        String lock = message.toString()+ "LOCK";
        if (expireKey.startsWith(PushCallback.DEVICE_PARAM_PREFIX)) {
            if(stringRedisTemplate.opsForValue().setIfAbsent(lock,"LOCK")){
                try {
                    //因为redis过期通知只通知key，所以这里只能冗余了。
                    String paramCacheJson = stringRedisTemplate.opsForValue().get(expireKey + "_V");
                    if (!StringUtils.isEmpty(paramCacheJson)) {
                        DeviceParamDo paramDo = JSONObject.parseObject(paramCacheJson, DeviceParamDo.class);
                        //将过期的缓存刷入到数据库
                        deviceParamMapper.updateById(paramDo);
                    }

                    stringRedisTemplate.delete(expireKey + "_V");

                } catch (Exception e) {
                    logger.error("[持久化参数] 异常",e);
                } finally {
                    stringRedisTemplate.delete(lock);
                }
            }

        }

    }
}
