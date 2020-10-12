package com.markbro.dzd.sso.core.filter;

import com.markbro.dzd.sso.core.SsoConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * 根据配置动态加载sso过滤器
 */
public class ApplicationSsoFilterInitializer implements WebApplicationInitializer {
    private static Logger logger = LoggerFactory.getLogger(ApplicationSsoFilterInitializer.class);

    /**
     * 运行环境参数key
     */
    private static final String ENVIROMENT_KEY = "app.env";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //单点登录设置
        if(!SsoConf.SSO_ENABLE){
            logger.info("设置为跳过SSO");
        }else{
            if("1".equals(SsoConf.SSO_FILTER_TYPE)){
                logger.warn("当前设置filterType=1,不执行加载SsoWebFilter!");//使用SsoRequestFilterInterceptor 代替该过滤器，该bean 需要手工在xml配置
                return;
            }
            try {
                //添加filter
                Dynamic addFilter = servletContext.addFilter("ssoWebFilter", new DelegatingFilterProxy());
                addFilter.setInitParameter("ssoServer", SsoConf.SSO_SERVER);
                addFilter.setInitParameter("loginPath", SsoConf.SSO_LOGIN_PATH);
                addFilter.setInitParameter("logoutPath", SsoConf.SSO_LOGOUT_PATH);
                addFilter.setInitParameter("excludedPaths", SsoConf.SSO_EXCLUDED_PATHS);

                addFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
                logger.info("动态添加sso filter！");
            } catch (Exception e) {
                logger.error("添加SSO filter出现错误");
                throw e;
            }
        }
    }
}
