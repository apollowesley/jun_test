package com.cdh.springboot.common;

import com.cdh.springboot.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 系统异常
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseData globalExceptionHandler(Exception e){
        log.error(e.getMessage());
        return ResponseData.error(ResponseCode.OPERATE_FAIL,e.getMessage());
    }

    /**
     * 业务异常，自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler({PermissionException.class})
    public ResponseData permissionExceptionHandler(PermissionException e){
        log.error(e.getMessage());
        return ResponseData.error(e.getCode(),e.getMessage());
    }

}
