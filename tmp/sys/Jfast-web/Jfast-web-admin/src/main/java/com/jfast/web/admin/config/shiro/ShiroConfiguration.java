package com.jfast.web.admin.config.shiro;import org.apache.shiro.cache.ehcache.EhCacheManager;import org.apache.shiro.mgt.SecurityManager;import org.apache.shiro.spring.LifecycleBeanPostProcessor;import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;import org.apache.shiro.spring.web.ShiroFilterFactoryBean;import org.apache.shiro.web.mgt.DefaultWebSecurityManager;import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.context.annotation.DependsOn;import javax.servlet.Filter;import java.util.HashMap;import java.util.Map;/** * @author zengjintao * @create 2019/6/17 10:00 * @since 1.0 **/@Configurationpublic class ShiroConfiguration {    @Bean    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();        shiroFilterFactoryBean.setSecurityManager(securityManager);        shiroFilterFactoryBean.setUnauthorizedUrl("/system/unAuth");        shiroFilterFactoryBean.setLoginUrl("/system/unAuth");        Map<String, String> filterChainDefinitionMap = new HashMap();        filterChainDefinitionMap.put("/ueditor/exec", "anon");        filterChainDefinitionMap.put("/uploads/**", "anon");        filterChainDefinitionMap.put("/upload/**", "anon");        filterChainDefinitionMap.put("/static/**", "anon");        filterChainDefinitionMap.put("/system/login", "anon");        filterChainDefinitionMap.put("/*", "anon");        filterChainDefinitionMap.put("/index.html", "anon");        filterChainDefinitionMap.put("/system/**", "authc");        filterChainDefinitionMap.put("/api/**", "authc");        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();        shiroFilterFactoryBean.setFilters(filterMap);        return shiroFilterFactoryBean;    }    @Bean    public EhCacheManager ehCacheManager() {        EhCacheManager ehCacheManager = new EhCacheManager();        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");        return ehCacheManager;    }    @Bean    public SecurityManager securityManager() {        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();        securityManager.setRealm(new SystemRealm());        securityManager.setCacheManager(this.ehCacheManager());        return securityManager;    }    @Bean    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {        return new LifecycleBeanPostProcessor();    }    @Bean    @DependsOn({"lifecycleBeanPostProcessor"})    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();        advisorAutoProxyCreator.setProxyTargetClass(true);        return advisorAutoProxyCreator;    }    @Bean    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);        return authorizationAttributeSourceAdvisor;    }}