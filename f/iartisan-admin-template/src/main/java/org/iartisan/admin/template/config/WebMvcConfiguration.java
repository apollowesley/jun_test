package org.iartisan.admin.template.config;

import org.iartisan.runtime.env.EnvContextConfig;
import org.iartisan.runtime.web.config.WebMvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author King
 * @since 2018/2/23
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfig implements ServletContextInitializer {

    @Autowired
    private EnvironmentInterceptor environmentInterceptor;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setAttribute("_title", EnvContextConfig.get("iartisan.admin.title"));
        servletContext.setAttribute("_env", EnvContextConfig.get("env", "demo"));
        servletContext.setAttribute("_authorEmail", EnvContextConfig.get("iartisan.author.email"));
        servletContext.setAttribute("staticVerison", EnvContextConfig.get("iartisan.static.version", "00000"));
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(environmentInterceptor).addPathPatterns("/**/**/deleteData", "/**/**/modifyData", "/userSupport/changeStatus", "/userSupport/modifyPwd");
    }

}
