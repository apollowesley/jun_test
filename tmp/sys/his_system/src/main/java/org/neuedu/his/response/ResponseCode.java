package org.neuedu.his.response;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/20-15:43
 **/
public enum ResponseCode {

    NOT_LOGIN(401,"用户未登录"),
    NOT_AUTHORIZED(403,"没有权限"),
    SUCCESS(200,"操作成功"),
    FAILURE(201,"操作失败"),
    SYSTEM_EXCEPTION(500,"系统异常"),
    BUSINESS_EXCEPTION(501,"业务逻辑异常"),
    NOT_HANDLER_EXCEPTION(404,"请求地址不合法"),
    PARAMS_VALIDATOR_ERROR(203,"参数不合法"),
    USERNAME_PASSWORD_ERROR(401,"用户名或密码输入有误"),
    ACCOUNT_DISABLED_EXCEPTION(401,"账户被禁用");

    int code;
    String message;

    ResponseCode(int code,String message){
        this.code = code;
        this.message = message;
    }

}
