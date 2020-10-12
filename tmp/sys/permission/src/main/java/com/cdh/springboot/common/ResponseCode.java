package com.cdh.springboot.common;

public enum ResponseCode {
    // 系统通用
    SUCCESS(200, "操作成功"),
    UN_LOGIN_ERROR(201, "没有登录"),
    OPERATE_FAIL(202, "操作失败"),
    // 用户
    USER_DISABLED(203, "用户已被禁用"),
    LOGIN_KAPTCHA_FAILED(204, "登陆验证码有误");

    private Integer code;
    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final Integer getCode() {
        return this.code;
    }

    public final String getMsg() {
        return this.msg;
    }

}
