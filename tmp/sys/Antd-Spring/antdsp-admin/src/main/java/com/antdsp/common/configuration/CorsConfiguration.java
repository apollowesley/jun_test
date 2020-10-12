package com.antdsp.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * <p>title:CorsConfiguration</p>
 * <p>Description: 跨域访问</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author lijiantao
 * @date 2019年6月13日
 * @email a496401006@qq.com
 *
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowCredentials(true)
            .allowedMethods("GET", "POST", "DELETE", "PUT")
            .maxAge(3600);;
	}

}
