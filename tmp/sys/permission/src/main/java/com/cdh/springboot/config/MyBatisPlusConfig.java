package com.cdh.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MyBatisPlusConfig {

    /**
     * 数据源配置
     * @return
     */
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    @Bean(value = "druidDataSource",initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * DruidSQL监控访问Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_USERNAME,"admin");
        servletRegistrationBean.addInitParameter(ResourceServlet.PARAM_NAME_PASSWORD,"admin");
        return servletRegistrationBean;
    }

    /**
     * Durid拦截器配置
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter(WebStatFilter.PARAM_NAME_EXCLUSIONS,"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}
