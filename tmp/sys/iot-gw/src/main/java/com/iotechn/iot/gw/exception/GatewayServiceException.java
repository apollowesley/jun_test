package com.iotechn.iot.gw.exception;

import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-11
 * Time: 下午10:59
 */
public class GatewayServiceException extends ServiceException {

    public GatewayServiceException(){}

    public GatewayServiceException(String message, int code) {
        super(message, code);
    }

    public GatewayServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }
}
