package com.markbro.dzd.sso.core.config;


import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.login.SsoTokenLoginHelper;
import com.markbro.dzd.sso.core.login.SsoWebLoginHelper;
import com.markbro.dzd.sso.core.store.*;
import com.markbro.dzd.sso.core.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 客户端和服务端都要继承该类来配置初始化Bean。
 * 服务端要额外配置SsoUserService接口的实现类。
 * 客户端要配置请求过滤器或拦截器。
 */
@Configuration
@PropertySource(value = "config/sso.properties")
public class SsoBaseConfig {
    protected   final Logger log= LoggerFactory.getLogger(getClass());
    @Value("${sso.redis.address}")
    protected String redisAddress;

    @Value("${sso.redis.expire.minite}")
    protected int redisExpireMinite;



    /*******1.  配置ISsoLoginHelper 根据配置文件config/sso.properties sso.server.bean.ISsoLoginHelper的值动态创建该接口的实例********************/
    @ConditionalOnProperty(name = "sso.server.bean.ISsoLoginHelper", havingValue = "SsoWebLoginHelper")
    @Bean("ssoLoginHelper")
    public ISsoLoginHelper getSsoWebLoginHelper(){
        log.info(">>>>>>>>>>>create ISsoLoginHelper,return new SsoWebLoginHelper();");
        return new SsoWebLoginHelper();
    }
    @ConditionalOnProperty(name = "sso.server.bean.ISsoLoginHelper", havingValue = "SsoTokenLoginHelper")
    @Bean("ssoLoginHelper")
    public ISsoLoginHelper getSsoTokenLoginHelper(){
        log.info(">>>>>>>>>>>create ISsoLoginHelper,return new SsoTokenLoginHelper();");
        return new SsoTokenLoginHelper();
    }
    @ConditionalOnMissingBean(ISsoLoginHelper.class)
    @Bean("ssoLoginHelper")
    public ISsoLoginHelper getDefaultSsoLoginHelper() {
        log.warn(">>>>>>>>>>>ISsoLoginHelper is missing,return new SsoWebLoginHelper()!");
        return new SsoWebLoginHelper();
    }
    /*******2. 配置ISsoLoginStore ***************/
    @Bean("ssoLoginStore")
    public ISsoLoginStore getISsoLoginStore(){
        log.info(">>>>>>>>>>>create ISsoLoginStore,return new SsoLoginStore(\""+redisAddress+"\",\""+redisExpireMinite+"\");");
        return new SsoLoginStore(redisAddress,redisExpireMinite);
    }
    /*******3. 配置 ISsoSessionIdHelper***********/
    @ConditionalOnProperty(name = "sso.server.bean.ISsoSessionIdHelper", havingValue = "MySsoSessionIdHelper")
    @Bean("ssoSessionIdHelper")
    public ISsoSessionIdHelper getMySsoSessionIdHelper(){
        log.info(">>>>>>>>>>>create ISsoLoginHelper,return new MySsoSessionIdHelper();");
        return new MySsoSessionIdHelper();
    }
    @ConditionalOnProperty(name = "sso.server.bean.ISsoSessionIdHelper", havingValue = "SsoSessionIdHelper")
    @Bean("ssoSessionIdHelper")
    public ISsoSessionIdHelper getSsoSessionIdHelper(){
        log.info(">>>>>>>>>>>create ISsoLoginHelper,return new SsoSessionIdHelper();");
        return new SsoSessionIdHelper();
    }
    @ConditionalOnMissingBean(ISsoSessionIdHelper .class)
    @Bean("ssoSessionIdHelper")
    public ISsoSessionIdHelper getDefaultSsoSessionIdHelper() {//没配置则生成默认的
        log.warn(">>>>>>>>>>>SsoSessionIdHelper is missing,return new SsoSessionIdHelper()!");
        return new SsoSessionIdHelper();
    }

    @Bean
    public SpringContextHolder getSpringContextHolder(){
        log.warn(">>>>>>>>>>>create SpringContextHolder,return new SpringContextHolder()!");
        return new SpringContextHolder();
    }
}
