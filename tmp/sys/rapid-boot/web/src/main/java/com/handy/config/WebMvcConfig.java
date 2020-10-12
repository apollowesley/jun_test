package com.handy.config;


import com.handy.interceptor.MemberLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author handy
 * @Description: {mvc配置拦截器}
 * @date 2019/9/2 18:13
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private MemberLoginInterceptor memberLoginInterceptor;


    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberLoginInterceptor).addPathPatterns("/**").excludePathPatterns(getExcludePath());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("entry/login");
        registry.addViewController("/index").setViewName("index");
    }

    /**
     * 排除路径
     *
     * @return
     */
    private List<String> getExcludePath() {
        List<String> patterns = new ArrayList<>();
        patterns.add("/");
        patterns.add("/api/entry/entry/send/captcha");
        patterns.add("/api/entry/entry/send/register");
        patterns.add("/api/entry/entry/register");

        patterns.add("/lib/**");
        patterns.add("/js/**");
        patterns.add("/images/**");
        patterns.add("/css/**");
        return patterns;
    }
}
