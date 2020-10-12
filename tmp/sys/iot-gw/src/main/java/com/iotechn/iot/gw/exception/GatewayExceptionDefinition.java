package com.iotechn.iot.gw.exception;


import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-11
 * Time: 下午11:00
 */
public class GatewayExceptionDefinition {

    /**
     * 范围 10001 ~~ 20000
     **/

    public static final ServiceExceptionDefinition GATEWAY_REGISTER_OPEN_API_FAILED
            = new ServiceExceptionDefinition(10001, "gateway open api register failed");

    public static final ServiceExceptionDefinition GATEWAY_METHOD_NOT_EXIST
            = new ServiceExceptionDefinition(10002, "gateway open api not exists");

    public static final ServiceExceptionDefinition GATEWAY_METHOD_NOT_ANNOTATION
            = new ServiceExceptionDefinition(10003, "gateway open api method not set annotation");

    public static final ServiceExceptionDefinition GATEWAY_PARAM_CHECK_FAILED
            = new ServiceExceptionDefinition(10004, "gateway open api param check failed");

    public static final ServiceExceptionDefinition GATEWAY_SYSTEM_INNER_UNKNOWN_EXCEPTION
            = new ServiceExceptionDefinition(10005, "gateway unknown exception");

    public static final ServiceExceptionDefinition GATEWAY_USER_NOT_LOGIN
            = new ServiceExceptionDefinition(10006, "您尚未登录");

    public static final ServiceExceptionDefinition GATEWAY_WS_REQUEST_NOT_DEFINE
            = new ServiceExceptionDefinition(10007,"request type has not define");

}
