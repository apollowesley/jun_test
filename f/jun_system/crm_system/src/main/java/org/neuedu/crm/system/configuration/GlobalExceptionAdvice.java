package org.neuedu.crm.system.configuration;

import lombok.extern.slf4j.Slf4j;
import org.neuedu.crm.system.exception.CrmSystemException;
import org.neuedu.crm.system.response.ResponseCode;
import org.neuedu.crm.system.response.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @description 全局异常处理,可以捕获初404外的所有异常
 * @auther: CDHONG.IT
 * @date: 2019/12/20-15:37
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(CrmSystemException.class)
    public ResponseEntity crmSystemException(CrmSystemException e){
        log.error("业务逻辑异常：{}",e.getMessage());
        return ResponseEntity.exception(ResponseCode.BUSINESS_EXCEPTION);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity notFoundException(Exception e){
        log.error("404请求找不到异常：{}",e.getMessage());
        return ResponseEntity.exception(ResponseCode.NOT_HANDLER_EXCEPTION);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessException(Exception e){
        log.error("403没有权限异常：{}",e.getMessage());
        return ResponseEntity.exception(ResponseCode.NOT_AUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity systemException(Exception e){
        log.error("系统异常：{}",e.getMessage());
        return ResponseEntity.exception(ResponseCode.SYSTEM_EXCEPTION);
    }

}
