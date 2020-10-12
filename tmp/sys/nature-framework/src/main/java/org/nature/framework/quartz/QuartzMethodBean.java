package org.nature.framework.quartz;

import java.lang.reflect.Method;

public class QuartzMethodBean {
	private Method method;
	private String cron;
	public QuartzMethodBean(Method method, String cron) {
		super();
		this.method = method;
		this.cron = cron;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}

	
}
