package com.star.test.advice;

import com.star.test.constant.FormConstant;
import com.star.test.dto.output.SimpleOutPutDTO;
import com.star.test.exception.TwException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SimpleOutPutDTO exceptionHandler(Throwable e) {
        SimpleOutPutDTO out = new SimpleOutPutDTO();
        if (e instanceof TwException) {
            TwException exception = (TwException) e;
            logger.error("Form表单系统异常,异常码={}，异常信息={}", exception.getErrCode(), exception.getErrMsg(), exception);
            out.setMsg(exception.getErrMsg());
            out.setCode(exception.getErrCode());
        } else {
            logger.error("系统异常,异常信息={}", e.getMessage(), e);
            out.setMsg(FormConstant.EXCEPTION_DEFAULT_MSG);
            out.setCode(FormConstant.EXCEPTION_CODE);
        }
        return out;
    }


    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public SimpleOutPutDTO exceptionMessageNotReadableHandler(Throwable e) {
        SimpleOutPutDTO out = new SimpleOutPutDTO();
        logger.error("系统异常,异常信息={}", e.getMessage(), e);
        out.setMsg(FormConstant.ARGUMENT_ERROR_MSG);
        out.setCode(FormConstant.EXCEPTION_CODE);
        return out;
    }

}