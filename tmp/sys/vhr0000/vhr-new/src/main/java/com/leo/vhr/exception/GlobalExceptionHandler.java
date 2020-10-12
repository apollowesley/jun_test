package com.leo.vhr.exception;

import com.leo.vhr.model.RespBean;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @description:
 * 全局异常处理类
 * @author: Leo
 * @createDate: 2020/2/16
 * @version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e){
        if(e instanceof MySQLIntegrityConstraintViolationException){
            return RespBean.error("该数据存在关联性，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
