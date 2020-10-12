package org.nature.framework.bean;

import java.lang.reflect.Method;
import java.util.List;

import org.nature.framework.interceptor.NatureInterceptor;

public class CtrlBean {
	private Class<?> cls;
	private Method method;
	private String namespace;
	private String url;
	private List<NatureInterceptor> interceptors;
	
	public Class<?> getCls() {
		return cls;
	}
	public Method getMethod() {
		return method;
	}
	
	public List<NatureInterceptor> getInterceptors() {
		return interceptors;
	}
	public String getNamespace() {
		return namespace;
	}
	public String getUrl() {
		return url;
	}
	public CtrlBean(Class<?> cls, Method method, String namespace, String url, List<NatureInterceptor> interceptors) {
		super();
		this.cls = cls;
		this.method = method;
		this.namespace = namespace;
		this.url = url;
		this.interceptors = interceptors;
	}
	@Override
	public String toString() {
		return "CtrlBean [cls=" + cls + ", method=" + method + ", namespace=" + namespace + ", url=" + url
				+ ", interceptors=" + interceptors + "]";
	}
	
}
