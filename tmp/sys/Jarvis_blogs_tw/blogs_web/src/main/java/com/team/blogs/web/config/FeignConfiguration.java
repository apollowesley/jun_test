package com.team.blogs.web.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/7/12
 * @Version: 1.0
 */
@Configuration
public class FeignConfiguration {


    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("xiaokai", "xiaokai123456");
    }
}
