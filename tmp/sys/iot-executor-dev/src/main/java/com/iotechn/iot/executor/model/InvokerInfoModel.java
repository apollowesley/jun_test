package com.iotechn.iot.executor.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-11-03
 * Time: 下午5:06
 */
public class InvokerInfoModel implements Serializable {

    private String invokerId;

    private String invokerIp;

    private String secretKey;

    public String getInvokerId() {
        return invokerId;
    }

    public void setInvokerId(String invokerId) {
        this.invokerId = invokerId;
    }

    public String getInvokerIp() {
        return invokerIp;
    }

    public void setInvokerIp(String invokerIp) {
        this.invokerIp = invokerIp;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
