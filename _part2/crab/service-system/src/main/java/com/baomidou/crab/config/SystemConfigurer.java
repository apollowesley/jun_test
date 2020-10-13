package com.baomidou.crab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.baomidou.crab.common.CrabConfigurer;
import com.baomidou.crab.common.LoginHandlerInterceptor;
import com.baomidou.kisso.web.interceptor.SSOSpringInterceptor;


/**
 * <p>
 * WEB 初始化相关配置
 * </p>
 *
 * @author jobob
 * @since 2018-03-31
 */
@ControllerAdvice
@Configuration
public class SystemConfigurer extends CrabConfigurer {

    @Autowired
    private SystemProperties systemProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 正式演示环境开启方法阻断拦截器
        if (systemProperties.isOnline()) {
            registry.addInterceptor(new BlockMethodHandlerInterceptor()).excludePathPatterns("/sso/**", "/sys/user/unlock");
        }
        // SSO 授权拦截器
        SSOSpringInterceptor ssoInterceptor = new SSOSpringInterceptor();
        ssoInterceptor.setHandlerInterceptor(new LoginHandlerInterceptor());
        registry.addInterceptor(ssoInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/sso/**", "/resource/nav_menu");
        // 注入跟踪访问日志
//        registry.addInterceptor(new LogInterceptor());
    }

    /**
     * <p>
     * 跨域同源策略配置
     * </p>
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8088");
        config.addAllowedOrigin("http://crab.baomidou.com");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
