package com.iotechn.iot.device.entity.exception;


import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-20
 * Time: 上午10:03
 */
public class DeviceServiceException extends ServiceException implements Serializable {

    public DeviceServiceException(){}

    public DeviceServiceException(String message, int code) {
        super(message, code);
    }

    public DeviceServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }
}
