package com.markbro.dzd.sso.client.web.demo;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.config.SsoBaseConfig;
import com.markbro.dzd.sso.core.filter.SsoWebFilterClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "config/sso.properties")
public class SsoClientConfig extends SsoBaseConfig {


    //配置文件sso.filterType=0表示客户端使用SsoWebFilter过滤请求
    @ConditionalOnProperty(name = "sso.filterType", havingValue = "0")
    @Bean
    public FilterRegistrationBean createFilterRegistrationBean(){
        log.info(">>>>>>>>>>>create filter SsoWebFilterClent()");
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("ssoWebFilterClent");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoWebFilterClient());

        registration.addInitParameter("ssoServer", SsoConf.SSO_SERVER);
        registration.addInitParameter("loginPath", SsoConf.SSO_LOGIN_PATH);
        registration.addInitParameter("logoutPath", SsoConf.SSO_LOGOUT_PATH);
        registration.addInitParameter("excludedPaths", SsoConf.SSO_EXCLUDED_PATHS);
        return registration;
    }


}
