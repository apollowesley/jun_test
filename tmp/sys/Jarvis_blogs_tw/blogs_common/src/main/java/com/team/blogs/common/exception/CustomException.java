package com.team.blogs.common.exception;

/**
 * @Author: xiaokai
 * @Description: 自定义异常
 * @Date: 2019/6/26
 * @Version: 1.0
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 4564124491192825748L;

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CustomException() {
        super();
    }

    public CustomException(int code, String message) {
        super(message);
        this.setCode(code);
    }

}
