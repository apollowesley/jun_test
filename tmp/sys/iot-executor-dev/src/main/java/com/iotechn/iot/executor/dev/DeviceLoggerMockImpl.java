package com.iotechn.iot.executor.dev;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-01-12
 * Time: 下午4:04
 */
public class DeviceLoggerMockImpl implements IDeviceLogger {
    public Boolean log(String secretKey, String key,String value) {
        System.out.print("记录日志:" + key + "=" + value);
        return true;
    }
}
