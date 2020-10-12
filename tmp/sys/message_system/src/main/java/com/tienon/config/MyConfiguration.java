package com.tienon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration inter = registry.addInterceptor(new SecurityInterceptor());
		inter.addPathPatterns("/leave/**");
		inter.excludePathPatterns("/user**");
	}
	
}
