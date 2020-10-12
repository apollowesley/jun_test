package com.iotechn.iot.executor.entity.exception;


import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-15
 * Time: 下午7:26
 */
public class ExecutorExceptionDefinition {
    /** 错误码范围 30001 ——— 40000 **/
    public static final ServiceExceptionDefinition EXECUTOR_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(30000,"executor unkonw exception");

    public static final ServiceExceptionDefinition EXECUTOR_NOT_EXIST =
            new ServiceExceptionDefinition(30001,"executor is not exist or does not belong to you!");

    public static final ServiceExceptionDefinition EXECUTOR_METHOD_NOT_EXIST =
            new ServiceExceptionDefinition(30002,"executor method is not exist!");

    public static final ServiceExceptionDefinition EXECUTOR_COMPILATION_EXCEPTION =
            new ServiceExceptionDefinition(30003, "编译错误，请检查语法");

    public static final ServiceExceptionDefinition EXECUTOR_NOT_AUDITING =
            new ServiceExceptionDefinition(30004,"executor not audited");

}
