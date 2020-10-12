package com.site.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 业务异常类
 * 
 * @author wangxin
 * @date 2014-02-11
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1144969267587138347L;

    String code = "-1";

    String message;

    Exception cause;

    public BusinessException() {
        super();
    }

    public BusinessException(String code) {
        super();
        this.code = code;
        this.message = ExceptionMsg.getErrorMsg(code);
        if(StringUtils.isBlank(this.message)){
            this.message =code ;//上线后可考虑移除，也可以不移除
        }
    }

    public BusinessException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message, Exception cause) {
        super();
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getCause() {
        return cause;
    }

    public void setCause(Exception cause) {
        this.cause = cause;
    }

}