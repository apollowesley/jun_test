package com.iotechn.iot.device.entity.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-09-16
 * Time: 下午10:30
 */
public class IotExchangeModel implements Serializable {

    public static final int TYPE_PARAM = 1;

    public static final int TYPE_INVOKE = 2;

    /**
     * type:1.设备参数  2.远程调用
     */
    private int type;
    private String name;
    private String method;
    private String param;
    private String uuid;
    private String secretKey;
    private long time;
    private String msg;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
