package com.tienon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.tienon.config.SecurityInterceptor;

@SpringBootApplication
@MapperScan("com.tienon.mapper")
public class LeaveStart extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(LeaveStart.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 内置的controller处理项目访问的"/",默认首页
		registry.addViewController("/").setViewName("forward:/login.html");
		// 当前的处理"/"是所有controller转发的最高级,有相同controller编写无效
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		// 执行父类方法,执行其他逻辑
		super.addViewControllers(registry);
	}

}
