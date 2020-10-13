package com.laycms.util;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class EncodingPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof RequestMappingHandlerAdapter) {
			List<HttpMessageConverter<?>> convs = ((RequestMappingHandlerAdapter) bean).getMessageConverters();
			for(int i=0;i<convs.size();i++){
				if(convs.get(i) instanceof StringHttpMessageConverter){
					//处理SpringMVC ResponseBody返回处理的字符集问题
					StringHttpMessageConverter   httpMessageConverter = new com.laycms.util.StringHttpMessageConverter();
					
					convs.set(i, httpMessageConverter);
				}
			}

			((RequestMappingHandlerAdapter) bean).setMessageConverters(convs);
			convs = ((RequestMappingHandlerAdapter) bean).getMessageConverters();
			return bean;
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

}
