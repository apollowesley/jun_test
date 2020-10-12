package com.cdh.springboot.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("请求参数监听：-->url:{},params:{}",requestURI,objectMapper.writeValueAsString(parameterMap));
    }

}
