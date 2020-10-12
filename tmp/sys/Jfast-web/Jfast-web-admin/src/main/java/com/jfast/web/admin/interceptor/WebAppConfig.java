/**
 * 
 */
package com.jfast.web.admin.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


/**
 * 系统配置
 * @author zengjintao
 * @version 1.0
 * @create_at 2017年9月27日下午8:03:45
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired
	private LogInterceptor logInterceptor;
	@Autowired
	private ParamsValidateInterceptor paramsValidateInterceptor;
	@Autowired
	private AuthInterceptor authInterceptor;

	@Value("${file.uploadPath}")
	private String uploadPath;

	//不需要拦截的url
	private static final List<String> noInterceptorUrl = new ArrayList<String>() {
		{
			add("/system/unAuth");
			add("/system/login");
		}
	};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authInterceptor).excludePathPatterns(noInterceptorUrl).addPathPatterns("/system/**").addPathPatterns("/api/**");
		registry.addInterceptor(paramsValidateInterceptor).addPathPatterns("/**");
	}

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 //配置静态资源路径
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		//配置文件上传虚拟路径
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath);
	}
}
