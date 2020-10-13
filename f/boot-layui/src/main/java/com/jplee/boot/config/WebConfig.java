package com.jplee.boot.config;

import com.jplee.boot.component.LoginHandlerInterceptor;
import com.jplee.boot.component.WebLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jplee
 * 可以使用WebMvcConfigurerAdapter来拓展springmvc的功能
 * @create 2019-01-23 23:31
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        // 浏览器发送 /ice 请求，同样展示success页面，但是不读取数据
        registry.addViewController("/ice").setViewName("success");
    }*/

    //所有WebMvcConfigurerAdapter组件会一起启用,首页设置
    @Bean//将组件注入到容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry){
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/login.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("main");
            }
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(new LoginHandlerInterceptor().getUrl());
            }
        };
        return adapter;
    }

    //将自定义的区域解析器加入到容器中来/注册国际化组件
    @Bean
    public LocaleResolver localeResolver(){
        return new WebLocaleResolver();
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(loginHandlerInterceptor().getUrl());
    }


    @Bean(name = "loginHandlerInterceptor")
    public LoginHandlerInterceptor loginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }*/
}
