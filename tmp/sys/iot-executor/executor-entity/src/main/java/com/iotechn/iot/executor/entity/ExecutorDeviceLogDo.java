package com.iotechn.iot.executor.entity;

import com.iotechn.iot.commons.entity.CommonsDO;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-01-12
 * Time: 下午4:18
 */
public class ExecutorDeviceLogDo extends CommonsDO implements Serializable {

    private String secretKey;

    private String logKey;

    private String logValue = "";

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getLogKey() {
        return logKey;
    }

    public void setLogKey(String logKey) {
        this.logKey = logKey;
    }

    public String getLogValue() {
        return logValue;
    }

    public void setLogValue(String logValue) {
        this.logValue = logValue;
    }
}
