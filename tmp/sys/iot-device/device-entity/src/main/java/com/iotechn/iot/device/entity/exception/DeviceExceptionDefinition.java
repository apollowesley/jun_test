package com.iotechn.iot.device.entity.exception;


import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-20
 * Time: 上午10:03
 */
public class DeviceExceptionDefinition {
    /** 错误码范围 20001 ——— 30000 **/
    public static final ServiceExceptionDefinition DEVICE_UNKNOWN_EXCEPTION = new ServiceExceptionDefinition(20000,"device system unknown exception");

    public static final ServiceExceptionDefinition DEVICE_DEVICE_NOT_EXIST = new ServiceExceptionDefinition(20001,"设备不存在");

    public static final ServiceExceptionDefinition DEVICE_COMMAND_NOT_EXIST = new ServiceExceptionDefinition(20002,"预设命令不存在");

    public static final ServiceExceptionDefinition DEVICE_HAS_EXIST = new ServiceExceptionDefinition(20003,"name已经存在");

    public static final ServiceExceptionDefinition DEVICE_NOT_OWNS_YOU = new ServiceExceptionDefinition(20004, "设备不属于你");
}
