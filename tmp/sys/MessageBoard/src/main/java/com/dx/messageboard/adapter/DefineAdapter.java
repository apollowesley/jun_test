package com.dx.messageboard.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 定义登录拦截器
 * Create by zhoushiyu
 */
@SpringBootConfiguration
public class DefineAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    public LoginInterceptor loginInterceptor;//用户登录拦截器

    //自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    //用户登录拦截器
    registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
    super.addInterceptors(registry);
    }


}
