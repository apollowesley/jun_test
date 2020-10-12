package com.nikijava.elklog.webfilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: niki-common
 * @Package: com.niki.log
 * @ClassName: FilterConfig
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/8/26 16:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogTraceFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LogCostFilter");
        registration.setOrder(1);
        return registration;
    }
}