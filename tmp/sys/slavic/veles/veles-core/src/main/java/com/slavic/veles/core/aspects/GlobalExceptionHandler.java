package com.slavic.veles.core.aspects;

import com.slavic.veles.base.exception.ApplyException;
import com.slavic.veles.base.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public <T> ResponseEntity<T> handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = Optional.ofNullable(bindingResult.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank).filter(i -> i.length() < 10)
                .orElse("请求参数错误");
        log.error("{}-{}","请求参数错误",e);
        return ResponseEntity.FAIL(message);
    }

    @ExceptionHandler(value = ApplyException.class)
    public <T> ResponseEntity<T> handle(ApplyException e) {
        return ResponseEntity.FAIL(e.getEntryHeader());
    }

    @ExceptionHandler(value = Exception.class)
    public <T> ResponseEntity<T> handle(Exception e) {
        log.error("{}-{}","请求异常",e);
        return ResponseEntity.FAIL();
    }
}
