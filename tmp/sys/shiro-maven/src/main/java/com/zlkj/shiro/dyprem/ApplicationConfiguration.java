package com.zlkj.shiro.dyprem;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.zlkj.shiro.dyprem.execption.DefaultExceptionHandler;
/**
 * @2017年8月6日
 * @Description:系统入口配置
 */
@SpringBootApplication
@PropertySource(value={ "classpath:config.properties" })
@EnableTransactionManagement(proxyTargetClass=true)
public class ApplicationConfiguration extends SpringBootServletInitializer {
	@Autowired
	private Environment env;
	@Autowired
	private DataSource druidDataSource;
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("/*");
		return registration;
	}
	/**
	 * 外置Tomcat 启动入口
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApplicationConfiguration.class);
	}
	
	@Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource);
    }
	
	@Bean
	public org.springframework.jdbc.core.JdbcTemplate  jdbcTemplate(DataSource druidDataSource){
		return new JdbcTemplate(druidDataSource);
	}
	
	@Bean
	public DefaultExceptionHandler DefaultExceptionHandler(){
		return new DefaultExceptionHandler();
	}
	
	@Bean
	public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver(){
		return new ExceptionHandlerExceptionResolver();
	}
	
	
	
	public static void main(String[] args) {
		new SpringApplication(ApplicationConfiguration.class).run(args);
	}

}
