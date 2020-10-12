package pers.man.quickdevcore.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.man.quickdevcore.shiro.SessionDAO;
import pers.man.quickdevcore.shiro.UserRealm;

import java.util.LinkedHashMap;

/**
 * Shiro配置类
 *
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:53
 * @project quick-dev
 */
@Configuration
public class ShiroConfig {

    /**
     * 加密算法
     */
    @Value("${shiro.hash.algorith-name}")
    private String hashAlgorithmName = "MD5";
    /**
     * 加密次数
     */
    @Value("${shiro.hash.iterations}")
    private int hashIterations = 1024;
    /**
     * shiro缓存配置文件位置
     */
    @Value("${shiro.cache.config-file}")
    private String cacheConfigFile = "classpath:shiro-cache.xml";
    /**
     * 登录页面
     */
    @Value("${shiro.filter.login-url}")
    private String loginUrl = "/login.html";
    /**
     * 主页面
     */
    @Value("${shiro.filter.success-url}")
    private String successUrl = "/index.html";
    /**
     * 认证失败页面
     */
    @Value("${shiro.filter.unauthorized-url}")
    private String unauthorizedUrl = "/error.html";

    /**
     * 自定义Realm
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        //指定凭证匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    /**
     * shiro安全管理器
     * @param userRealm
     * @param ehCacheManager
     * @param sessionManager
     * @param rememberMeManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm, EhCacheManager ehCacheManager, SessionManager sessionManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(ehCacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    /**
     * Shiro过滤器
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //登录页面
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        //主页面
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        //认证失败页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/plugin/**","anon");
        filterChainDefinitionMap.put("/login.html","anon");
        return shiroFilterFactoryBean;
    }

    /**
     * 配置缓存管理器
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        //指定配置文件
//        ehCacheManager.setCacheManagerConfigFile(cacheConfigFile);
        return ehCacheManager;
    }

    /**
     * 配置Shiro Bean的生命周期方法自动的调用配置器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 配置开启shiro注解
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 配置session id生成器
     *
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 配置SessionDAO
     *
     * @param javaUuidSessionIdGenerator
     * @return
     */
    @Bean
    public SessionDAO sessionDAO(JavaUuidSessionIdGenerator javaUuidSessionIdGenerator) {
        SessionDAO sessionDAO = new SessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator);
        return sessionDAO;
    }

    /**
     * 配置会话管理器
     *
     * @param sessionDAO
     * @return
     */
    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(sessionDAO);
        return sessionManager;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setMaxAge(60 * 60 * 24 * 30);
        rememberMeManager.setCookie(simpleCookie);
        return rememberMeManager;
    }
}
