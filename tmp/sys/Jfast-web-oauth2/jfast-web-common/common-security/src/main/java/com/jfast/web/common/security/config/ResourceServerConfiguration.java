package com.jfast.web.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 资源配置中心
 */
@Configuration
@EnableResourceServer
// 开启权限注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint customAccessDeniedHandler;
    @Autowired
    private AccessDeniedHandler accessDeniedHandlerImpl;


    private static final String[] NO_AUTH_URL = new String[] {
        "/oauth/token", "/image", "/admin/findByName"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(NO_AUTH_URL)
                .permitAll()
                .anyRequest().authenticated(); // 配置访问控制，必须认证后才可以访问
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(customAccessDeniedHandler)
                .accessDeniedHandler(accessDeniedHandlerImpl);
    }
}
