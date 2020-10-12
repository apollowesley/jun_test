package com.jfast.web.common.security.config;

import com.alibaba.fastjson.JSONObject;
import com.jfast.web.common.core.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理url无权限访问处理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/10/20 14:05
 */
@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("权限不足,无法访问", accessDeniedException);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(new ResultCode(ResultCode.UN_AUTH_ERROR_CODE, "权限不足")));

    }
}
