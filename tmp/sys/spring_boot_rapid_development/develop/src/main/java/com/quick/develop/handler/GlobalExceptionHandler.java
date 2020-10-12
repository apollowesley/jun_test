package com.quick.develop.handler;

import com.quick.develop.model.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2019/11/25.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result validationHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String errorMessage = objectError.getDefaultMessage();
        String[] split = errorMessage.split(",");
        return new Result<>(Integer.parseInt(split[0]), split[1], null);
    }

}
