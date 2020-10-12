package com.team.blogs.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaokai
 * @Description: 定义全局异常处理
 * @Date: 2019/6/26
 * @Version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * 定义要捕获的异常 可以多个 @ExceptionHandler({})
     *
     * @param request  request
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(CustomException.class)
    public ErrorResponseEntity customExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        CustomException exception = (CustomException) e;
        return new ErrorResponseEntity(exception.getCode(), exception.getMessage());
    }

    /**
     * 捕获RuntimeException异常
     * TODO：这里只在 exceptionHandler 通过if (e instanceof xxxException) 注意：有点麻烦
     * TODO: 也可以通过写多个 exceptionHandler 来处理不同异常
     *
     * @param request  request
     * @param e        exception
     * @param response response
     * @return 响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponseEntity runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        RuntimeException exception = (RuntimeException) e;
        return new ErrorResponseEntity(400, exception.getMessage());
    }

    /**
     * 通用的接口映射异常处理方法
     *
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     * @return 响应结果
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            return new ResponseEntity<>(new ErrorResponseEntity(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
                    + ",信息：" + exception.getLocalizedMessage());
            return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
        }
        return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);

    }
}
