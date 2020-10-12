package com.iotechn.iot.commons.entity.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-04-03
 * Time: 下午11:26
 */
public class ServiceExceptionDefinition {
    private int code;
    private String msg;

    public ServiceExceptionDefinition(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
