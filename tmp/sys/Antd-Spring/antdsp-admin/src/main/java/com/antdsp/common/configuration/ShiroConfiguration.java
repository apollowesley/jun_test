package com.antdsp.common.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.antdsp.common.shiro.AntdspShiroRealm;
import com.antdsp.common.shiro.AntdspShiroSessionManager;

/**
 * 
 * <p>title: ShiroConfiguration</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author lijiantao
 * @date 2019年6月12日
 * @email a496401006@qq.com
 *
 */
@Configuration
public class ShiroConfiguration {
	
	@Bean
	public Realm shiroRealm() {
		AntdspShiroRealm realm = new AntdspShiroRealm();
		return realm;
	}
	
	@Bean("securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		securityManager.setSessionManager(shiroSessionManager());
		return securityManager;
	}
	
	@Bean
	public SessionManager shiroSessionManager() {
		AntdspShiroSessionManager sessionManger = new AntdspShiroSessionManager();
		return sessionManger;
	}
	
	@Bean
	public ShiroFilterFactoryBean shirofilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
		shiroFilterFactory.setSecurityManager(securityManager);
        
        Map<String , String> filterMap = new HashMap<>();
        filterMap.put("/login","anon");
        filterMap.put("/logout","anon");
        filterMap.put("/unauth","anon");
        filterMap.put("/captcha.jpg","anon");
        filterMap.put("/**","authc");
        
        shiroFilterFactory.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactory.setSuccessUrl("/login_success");
        shiroFilterFactory.setUnauthorizedUrl("/unauth");
        shiroFilterFactory.setLoginUrl("/login");
        
        return shiroFilterFactory;
	}
	
	//使用shiro的权限注解
	@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
	
}
