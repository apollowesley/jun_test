package org.nature4j.framework.quartz;

import java.lang.reflect.Method;
import java.util.Map;

public class QuartzJobBean {
	private Object quartzJobObject;
	private Map<String,QuartzMethodBean> methods;
	
	public Method getMethod(String methodName){
		return methods.get(methodName).getMethod();
	}
	
	public Object getQuartzJobObject() {
		return quartzJobObject;
	}
	public void setQuartzJobObject(Object quartzJobObject) {
		this.quartzJobObject = quartzJobObject;
	}

	public Map<String, QuartzMethodBean> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, QuartzMethodBean> methods) {
		this.methods = methods;
	}
	
}
