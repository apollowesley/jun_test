package com.jfast.web.common.security.config;

import com.alibaba.fastjson.JSONObject;
import com.jfast.web.common.core.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理匿名用户访问无权限资源时的异常
 * @author wangxue
 * @version 1.0
 * @create_at 2019/9/15 20:35
 */
@Component
@Slf4j
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        Throwable throwable = e.getCause();

        ResultCode resultCode = null;
        String message = null;
        if (throwable instanceof InvalidTokenException) {
            message = "token已失效";
            resultCode = new ResultCode(ResultCode.TOKEN_INVALID, message);
        }
        else {
            message = "权限不足";
            resultCode = new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, message);
        }
        log.error(message, e);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONObject.toJSONString(resultCode));
    }
}

