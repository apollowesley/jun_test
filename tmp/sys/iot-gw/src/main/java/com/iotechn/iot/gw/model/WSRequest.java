package com.iotechn.iot.gw.model;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-12-24
 * Time: 下午12:02
 */
public class WSRequest {

    private String secretKey;

    private Integer type;

    private String msg;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
