package com.markbro.dzd.sso.serverdemo;

import com.markbro.dzd.sso.server.config.SsoServerConfig;
import com.markbro.dzd.sso.server.service.SsoUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "config/sso.properties")
public class MySsoServerConf extends SsoServerConfig {
    @ConditionalOnProperty(name = "sso.server.bean.SsoUserService", havingValue = "MyUserServiceImpl")
    @Bean
    public SsoUserService getMySsoUserService(){
        log.info("4.>>>>>>>>>>> create SsoUserService,return new MyUserServiceImpl();");
        return new MyUserServiceImpl();
    }
}
