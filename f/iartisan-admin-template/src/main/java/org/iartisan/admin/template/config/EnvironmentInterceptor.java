package org.iartisan.admin.template.config;


import org.iartisan.runtime.env.EnvContextConfig;
import org.iartisan.runtime.exception.NotAllowedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 拦截demo环境的一些操作请求
 *
 * @author King
 * @since 2018/4/3
 */
@Component
public class EnvironmentInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //演示环境不支持删除操作
        String env = EnvContextConfig.get("env", "demo");
        if ("demo".equals(env)) {
            throw new NotAllowedException("demo环境不支持该操作");
        }
        return true;
    }
}