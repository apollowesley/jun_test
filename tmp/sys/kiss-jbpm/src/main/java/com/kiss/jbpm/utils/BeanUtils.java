package com.kiss.jbpm.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanUtils {
	public static ApplicationContext getAct(){
		return new ClassPathXmlApplicationContext("config/global-beans-context.xml");
	}
	
	public static Object getBean(String beanName){
		return getAct().getBean(beanName);
	}
}
