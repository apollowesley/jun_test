package com.bodsite.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class SpringUtil implements ApplicationContextAware{
	  private static ApplicationContext applicationContext;  

	    public void setApplicationContext(ApplicationContext applicationContext) {
	    	SpringUtil.applicationContext = applicationContext;  
	    }  

	    public static ApplicationContext getApplicationContext() {  
	        return applicationContext;  
	    }  
	  
	    public static Object getBean(String name) throws BeansException {  
	        return applicationContext.getBean(name);  
	    } 
	    
	    @SuppressWarnings("all")
	    public static <T> T getBean(Class<T> cls) throws BeansException {  
	        return (T) applicationContext.getBean(cls);  
	    } 
	    
	    public static<T> T getBean( String beanName , Class<T> clazz ){
	    	Object tempBean = applicationContext.getBean( beanName ) ;
	    	if( tempBean == null ){
	    		return null ;
	    	}
	    	return (T)tempBean  ;
	    }
	    
}
