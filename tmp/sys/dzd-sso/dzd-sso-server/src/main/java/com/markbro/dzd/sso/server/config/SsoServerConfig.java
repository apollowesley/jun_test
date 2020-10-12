package com.markbro.dzd.sso.server.config;


import com.markbro.dzd.sso.core.config.SsoBaseConfig;
import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.login.SsoTokenLoginHelper;
import com.markbro.dzd.sso.core.login.SsoWebLoginHelper;
import com.markbro.dzd.sso.core.store.*;
import com.markbro.dzd.sso.server.service.DemoSsoUserServiceImpl;
import com.markbro.dzd.sso.server.service.SsoUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "config/sso.properties")
public class SsoServerConfig extends SsoBaseConfig {

    /*******配置 SsoUserService********/
    @ConditionalOnMissingBean(SsoUserService .class)
    @Bean("ssoUserService")
    public SsoUserService getSsoUserService(){
        log.info("4.>>>>>>>>>>>ssoUserService is missing， return new DemoSsoUserServiceImpl()!");
        return new DemoSsoUserServiceImpl();
    }

}
