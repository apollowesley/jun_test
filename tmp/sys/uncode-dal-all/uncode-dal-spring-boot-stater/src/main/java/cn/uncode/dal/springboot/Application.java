package cn.uncode.dal.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.uncode.dal.springboot.interceptor.LoginInterceptor;
/**
 * Created by KevinBlandy on 2017/2/28 14:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = "cn.uncode.dal.springboot.mapper",annotationClass = Repository.class)
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String[] agrs){
		SpringApplication.run(Application.class,agrs);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login","/static/**");
	}
}
