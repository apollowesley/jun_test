package com.zlkj.shiro.dyprem;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zlkj.shiro.dyprem.bind.CurrentUserMethodArgumentResolver;
/**
 * 
 * @2017年8月6日
 * @Description: 视图层配置
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{
	/**
	 * 释放静态资源
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");//代表映射 webapp目录下的所有资源 进行静态资源释放
	}
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new CurrentUserMethodArgumentResolver());
    }
	
}
