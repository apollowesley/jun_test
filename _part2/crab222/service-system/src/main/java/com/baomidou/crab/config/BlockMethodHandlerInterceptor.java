package com.baomidou.crab.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baomidou.mybatisplus.extension.api.Assert;

/**
 * <p>
 * 方法阻断拦截器，禁止演示非法操作
 * </p>
 *
 * @author jobob
 * @since 2018-11-13
 */
public class BlockMethodHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Assert.fail(!"GET".equals(request.getMethod()), "演示模式无法执行该操作！");
        return super.preHandle(request, response, handler);
    }
}
