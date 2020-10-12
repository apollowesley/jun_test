package com.iotechn.iot.executor.entity.exception;


import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-15
 * Time: 下午7:25
 */
public class ExecutorServiceException extends ServiceException implements Serializable {

    public ExecutorServiceException() {
    }

    public ExecutorServiceException(String message, int code) {
        super(message, code);
    }

    public ExecutorServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }
}
