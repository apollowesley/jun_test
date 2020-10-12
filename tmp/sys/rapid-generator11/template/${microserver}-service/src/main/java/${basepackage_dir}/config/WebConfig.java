package ${basepackage}.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

<#include "java_author.include">
public class WebConfig extends WebMvcConfigurerAdapter {

/*	@Autowired
    private StatInterceptor statInterceptor;
	@Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private CorsInterceptor corsInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(statInterceptor).addPathPatterns("/**");
		registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authInterceptor).addPathPatterns("/user/**");
	}*/

}
