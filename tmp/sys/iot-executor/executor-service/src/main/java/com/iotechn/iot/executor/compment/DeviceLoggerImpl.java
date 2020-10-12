package com.iotechn.iot.executor.compment;

import com.iotechn.iot.executor.dev.IDeviceLogger;
import com.iotechn.iot.executor.entity.ExecutorDeviceLogDo;
import com.iotechn.iot.executor.mapper.ExecutorDeviceLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-01-12
 * Time: 下午4:15
 */
@Component
public class DeviceLoggerImpl implements IDeviceLogger {

    @Autowired
    private ExecutorDeviceLogMapper executorDeviceLogMapper;

    @Override
    public Boolean log(String secretKey, String key,String value) {
        ExecutorDeviceLogDo executorDeviceLogDo = new ExecutorDeviceLogDo();
        if (StringUtils.isEmpty(secretKey) || StringUtils.isEmpty(key)) {
            return false;
        }
        executorDeviceLogDo.setSecretKey(secretKey);
        executorDeviceLogDo.setLogKey(key);
        executorDeviceLogDo.setLogValue(value);
        executorDeviceLogMapper.insert(executorDeviceLogDo);
        return true;
    }
}
