package com.iotechn.iot.executor.dev;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-01-12
 * Time: 下午4:03
 */
public interface IDeviceLogger {

    /**
     * 记录设备日志，可以从OpenApi中拉到记录的数据
     * @param secretKey 设备的secretKey 可以从invokeContext.invokerInfoModel 中获取
     * @param key 日志的key，或日志的分类。 通过此key 可查出list
     * @param value 日志记录的raw数据
     * @return
     */
    public Boolean log(String secretKey, String key,String value);

}
