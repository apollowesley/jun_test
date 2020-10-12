package com.myapp.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.oschina.durcframework.easymybatis.ExternalConfigBean;
import net.oschina.durcframework.easymybatis.support.DateFillInsert;

/**
 * 全局配置
 * @author tanghc
 *
 */
@Configuration
public class GlobalConfig {

	@Bean
	public ExternalConfigBean externalConfigBean() {
		ExternalConfigBean bean = new ExternalConfigBean();
		bean.setFills(Arrays.asList(new DateFillInsert("add_time")));
		
		return bean;
	}
	
}
