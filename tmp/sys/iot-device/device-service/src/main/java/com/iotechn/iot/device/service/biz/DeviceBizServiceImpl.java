package com.iotechn.iot.device.service.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.device.entity.DeviceDo;
import com.iotechn.iot.device.entity.exception.DeviceExceptionDefinition;
import com.iotechn.iot.device.entity.exception.DeviceServiceException;
import com.iotechn.iot.device.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-18
 * Time: 上午10:50
 */
@Service("deviceBizService")
public class DeviceBizServiceImpl {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String DEVICE_SK_PREFIX = "D_SK_";

    public DeviceDo getDeviceBySK(String secretKey) throws ServiceException {

        String json = stringRedisTemplate.opsForValue().get(DEVICE_SK_PREFIX + secretKey);

        if(!StringUtils.isEmpty(json)) {
            DeviceDo deviceDo = JSONObject.parseObject(json, DeviceDo.class);
            if (deviceDo != null) {
                return deviceDo;
            }
        }

        List<DeviceDo> deviceDos = deviceMapper.selectList(new EntityWrapper<DeviceDo>().eq("secret_key", secretKey));
        if (CollectionUtils.isEmpty(deviceDos)) {
            //TODO 这里有缓存穿透的风险
            throw new DeviceServiceException(DeviceExceptionDefinition.DEVICE_DEVICE_NOT_EXIST);
        }

        stringRedisTemplate.opsForValue().set(DEVICE_SK_PREFIX + secretKey, JSONObject.toJSONString(deviceDos.get(0)), 30, TimeUnit.MINUTES);

        return deviceDos.get(0);
    }

    public void refreshDeviceCache(DeviceDo deviceDo) {
        stringRedisTemplate.opsForValue().set(DEVICE_SK_PREFIX + deviceDo.getSecretKey(), JSONObject.toJSONString(deviceDo), 30 , TimeUnit.MINUTES);
    }

    public void deleteCache(String secretKey) {
        stringRedisTemplate.delete(DEVICE_SK_PREFIX + secretKey);
    }
}
