package com.lemo.servlet.method1;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Conf {
	
    @Bean
	public ServletRegistrationBean MyServlet1() {
		return new ServletRegistrationBean(new MyServlet(), "/myServlet/*");
	}
}
