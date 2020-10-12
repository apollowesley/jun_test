package com.antdsp.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil{
	
	private static ApplicationContext applicationContext ;
	
	public static void setApplicationContext(ApplicationContext context) {
		if(ApplicationContextUtil.applicationContext == null) {
			ApplicationContextUtil.applicationContext = context;
		}
	}
	
	public static String getActiveProfile() {
		return applicationContext.getEnvironment().getActiveProfiles()[0];
	}

}
