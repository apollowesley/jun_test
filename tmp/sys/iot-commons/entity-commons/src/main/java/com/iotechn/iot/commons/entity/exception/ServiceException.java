package com.iotechn.iot.commons.entity.exception;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-11
 * Time: 下午10:55
 */
public abstract class ServiceException extends Exception implements Serializable {
    private int code;

    public ServiceException() {
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(ServiceExceptionDefinition definition) {
        super(definition.getMsg());
        this.code = definition.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
