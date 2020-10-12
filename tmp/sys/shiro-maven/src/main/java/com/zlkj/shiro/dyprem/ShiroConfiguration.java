package com.zlkj.shiro.dyprem;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.zlkj.shiro.dyprem.credentials.RetryLimitHashedCredentialsMatcher;
import com.zlkj.shiro.dyprem.filter.SysUserFilter;
import com.zlkj.shiro.dyprem.realm.UserRealm;
import com.zlkj.shiro.dyprem.spring.CustomDefaultFilterChainManager;
import com.zlkj.shiro.dyprem.spring.CustomPathMatchingFilterChainResolver;
/**
 * 权限框架SHiro组件配置
 * @2017年7月28日
 * @Description:
 */
@Configuration
public class ShiroConfiguration implements EnvironmentAware  {//实现EnvironmentAware  注入env 环境 不然为null
	private Environment env;
	@Override
	public void setEnvironment(Environment environment) {
		this.env=environment;
	}
	/**
	 * 相当于在 web.xml配置的 DelegatingFilterProxy过滤器
	 * @return
	 */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.addInitParameter("targetBeanName", "shiroFilter");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }
	/**
	 * 会话管理
	 * @return
	 */
	@Bean
	public  DefaultWebSessionManager defaultWebSessionManager(SimpleCookie simpleCookie,
			EnterpriseCacheSessionDAO enterpriseCacheSessionDAO){
		DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(Long.parseLong(//
					env.getProperty("shiro.globalSessionTimeout")));
		sessionManager.setDeleteInvalidSessions(Boolean.parseBoolean(//
							env.getProperty("shiro.deleteInvalidSessions")));
		sessionManager.setSessionValidationSchedulerEnabled(//
					Boolean.parseBoolean(env.getProperty("shiro.sessionValidationSchedulerEnabled")));
		sessionManager.setSessionIdCookie(simpleCookie);
		sessionManager.setSessionDAO(enterpriseCacheSessionDAO);
		return sessionManager;
		
	}
	/**
	 * 会话Id生成器  删除
	 * @return
	 */
	@Bean
	public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator(){
		 return new JavaUuidSessionIdGenerator();
	}
	/**
	 * 会话DAO
	 * @return
	 */
	@Bean
	public EnterpriseCacheSessionDAO sessionDAO(JavaUuidSessionIdGenerator javaUuidSessionIdGenerator){
		EnterpriseCacheSessionDAO enterSessionDAO=new EnterpriseCacheSessionDAO();
		enterSessionDAO.setActiveSessionsCacheName(env.getProperty("shiro.activeSessionCacheName"));
		enterSessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator);
		return enterSessionDAO;
	}
	/**
	 * cookies 会话模板
	 * @return
	 */
	@Bean
	public SimpleCookie simpleCookie(){
		SimpleCookie  simpleCookie=new SimpleCookie("sessionId");
		simpleCookie.setHttpOnly(Boolean.parseBoolean(env.getProperty("shiro.session.cookie.httpOnly")));
		simpleCookie.setMaxAge(Integer.parseInt(env.getProperty("shiro.session.cookie.makAge")));
		return simpleCookie;
	}
	
	@Bean
	public SimpleCookie rememberMesimpleCookie(){
		SimpleCookie  simpleCookie=new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(Boolean.parseBoolean(env.getProperty("shiro.rememberMe.cookie.httpOnly")));
		simpleCookie.setMaxAge(Integer.parseInt(env.getProperty("shiro.rememberMe.cookie.makAge")));
		return simpleCookie;
	}
	/**
	 * RememberMe 管理器
	 * @return
	 */
	@Bean
	public CookieRememberMeManager cookieRememberMeManager(SimpleCookie rememberMesimpleCookie){
		CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(org.apache.shiro.codec.Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		cookieRememberMeManager.setCookie(rememberMesimpleCookie);
		return cookieRememberMeManager;
	}
	
	/**
	 * 配置Shiro凭证匹配器
	 */
	@Bean
	public  RetryLimitHashedCredentialsMatcher credentialsMatcher(EhCacheManager ehCacheManager){
			RetryLimitHashedCredentialsMatcher credentialsMatcher=
					new RetryLimitHashedCredentialsMatcher(ehCacheManager);
			credentialsMatcher.setHashAlgorithmName(env.getProperty("shiro.hashAlgorithmName"));
			credentialsMatcher.setHashIterations(Integer.parseInt(
						env.getProperty("shiro.HashIterations")));
			credentialsMatcher.setStoredCredentialsHexEncoded(//
					Boolean.parseBoolean(env.getProperty("shiro.storedCredentialsHexEncoded")));
		 return credentialsMatcher;
	}
	
	/**
	 * 自定义 Realm
	 * @return
	 */
	@Bean
	public UserRealm authenticationRealm(RetryLimitHashedCredentialsMatcher credentialsMatcher, EhCacheManager  shiroEhCacheManager){
		UserRealm	authenticationRealm=new UserRealm();
		authenticationRealm.setCredentialsMatcher(credentialsMatcher);
		authenticationRealm.setCacheManager(shiroEhCacheManager);
		authenticationRealm.setCachingEnabled(Boolean.FALSE);
		return authenticationRealm;
	}
	/**
	 *  安全认证管理器
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager(DefaultWebSessionManager defaultWebSessionManager,
			UserRealm authenticationRealm,CookieRememberMeManager 
			cookieRememberMeManager,EhCacheManager shiroEhCacheManager ){
			DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
			defaultWebSecurityManager.setRealm(authenticationRealm);
			defaultWebSecurityManager.setSessionManager(defaultWebSessionManager); 
			defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
			defaultWebSecurityManager.setCacheManager(shiroEhCacheManager);
		return 	defaultWebSecurityManager;
	}
	/**
	 * 通过MethodInvokingFactoryBean工厂Bean，可以将指定方法返回值注入成为目标Bean的属性值，MethodInvokingFactoryBean用来获得指定方法的返回值，该方法可以是静态方法 
	 * 也可以是实例方法。 获得的方法返回值既可以被注入到指定Bean实例的指定属性，也可以直接定义成Bean实例。 
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
		MethodInvokingFactoryBean methodInvokingFactoryBean=new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod(
				env.getProperty("shiro.static.method"));
		methodInvokingFactoryBean.setArguments(
					new Object[]{defaultWebSecurityManager});
		return methodInvokingFactoryBean;
	}
	
	/**
	 * url路径匹配过滤器扩展
	 * @return
	 */
	@Bean
	public CustomPathMatchingFilterChainResolver filterChainResolver(CustomDefaultFilterChainManager filterChainManager){
		CustomPathMatchingFilterChainResolver filterChainResolver=new
				CustomPathMatchingFilterChainResolver();
		filterChainResolver.setCustomDefaultFilterChainManager(filterChainManager);
		return filterChainResolver;
	}
	
	/**
    <bean class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
        <property name="targetObject" ref="shiroFilter"/>
        <property name="targetMethod" value="setFilterChainResolver"/>
        <property name="arguments" ref="filterChainResolver"/>
    </bean>
	 * 相当于： shiroFilter.setFilterChainResolver(pathMatchingFilterChainResolver());
	 * @throws Exception 
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingBean(ShiroFilterFactoryBean shiroFilter,
			PathMatchingFilterChainResolver filterChainResolver) throws Exception{
		MethodInvokingFactoryBean methodInvokingFactoryBean=new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setTargetObject(shiroFilter.getObject());
		methodInvokingFactoryBean.setArguments(new Object[]{filterChainResolver});
		methodInvokingFactoryBean.setTargetMethod("setFilterChainResolver");
		return methodInvokingFactoryBean;
	}

	@Bean
	public AbstractShiroFilter AbstractShiroFilter(ShiroFilterFactoryBean shiroFilter,
			CustomPathMatchingFilterChainResolver filterChainResolver){
		AbstractShiroFilter abstractShiroFilter;
		try {
			abstractShiroFilter = (org.apache.shiro.web.servlet.AbstractShiroFilter)
					shiroFilter.getObject();
			abstractShiroFilter.setFilterChainResolver(filterChainResolver);
			return abstractShiroFilter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Shiro 主过滤器
	 * @return
	 * @throws Exception 
	 */
	@Bean(name="shiroFilter") //该shiroFilter  对应 new DelegatingFilterProxy("shiroFilter")
	public org.apache.shiro.spring.web.ShiroFilterFactoryBean  shiroFilterFactoryBean(
			DefaultWebSecurityManager defaultWebSecurityManager,PathMatchingFilterChainResolver filterChainResolver) throws Exception{
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
		
		org.apache.shiro.web.servlet.AbstractShiroFilter abstractShiroFilter=
				(AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		abstractShiroFilter.setFilterChainResolver(filterChainResolver);
		
		
		return  shiroFilterFactoryBean; 
	}
	/**
	 * 自定义过滤器链管理器扩展
	 * @return
	 */
	@Bean
	public CustomDefaultFilterChainManager  filterChainManager(FormAuthenticationFilter formAuthenticationFilter,SysUserFilter sysUserFilter){
		CustomDefaultFilterChainManager chainManager=new CustomDefaultFilterChainManager();
		chainManager.setLoginUrl("/login");
		chainManager.setSuccessUrl("/");
		chainManager.setUnauthorizedUrl("/unauthorized");
		/**
		 * 自定义的Filter
		 */
	  	Map<String,Filter>customFilters=new HashMap<String,Filter>();
		customFilters.put("authc", formAuthenticationFilter);
		customFilters.put("sysUser", sysUserFilter);
		
		chainManager.setCustomFilters(customFilters);
		try {
				chainManager.setDefaultFilterChainDefinitions(
						FileUtils.readFileToString(new DefaultResourceLoader(
								).getResource(env.getProperty("shiro.filter.link")).getFile(),"UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return chainManager;
	}
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter(){
		FormAuthenticationFilter filter=new FormAuthenticationFilter();
		filter.setUsernameParam("username");
		filter.setPasswordParam("password");
		filter.setRememberMeParam("rememberMe");
		filter.setLoginUrl("/login");
		return filter;
	}
	@Bean
	public com.zlkj.shiro.dyprem.filter.SysUserFilter sysUserFilter(){
		return new SysUserFilter();
	}
	/**
	 * 身份信息 权限信息缓存
	 * @return
	 */
	@Bean
	public  org.apache.shiro.cache.ehcache.EhCacheManager shiroEhCacheManager(){
    	EhCacheManager eManager=new EhCacheManager();
    	eManager.setCacheManagerConfigFile(env.getProperty("shiro.cache.filePath"));
	   return eManager;
   }
	
	/**
	 * Shiro生命周期处理器
	 * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调
	 * @return
	 */
	@Bean(name="lifecycleBeanPostProcessor")
	public org.apache.shiro.spring.LifecycleBeanPostProcessor  lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
	
	
	/**
	 * 决定对哪些类的方法进行AOP代理。
	 */
    @Bean
    @ConditionalOnMissingBean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    
    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法(如@RequiresRoles，@RequiresPermissions)，。
     */
    @Bean
    public   AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){
    	AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
    	advisor.setSecurityManager(defaultWebSecurityManager);
    	return advisor;
    }
	    
    
	 
}
