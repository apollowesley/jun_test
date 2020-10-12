package org.nature4j.framework.bean;

import java.lang.reflect.Method;
import java.util.List;

import org.nature4j.framework.enums.AskType;
import org.nature4j.framework.interceptor.NatureInterceptor;
import org.nature4j.framework.validator.NatureValidator;

public class CtrlBean {
	private Class<?> cls;
	private Method method;
	private String namespace;
	private String url;
	private List<NatureInterceptor> interceptors;
	private List<NatureValidator> validators;
	private AskType askType;
	
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

	public CtrlBean(Class<?> cls, Method method, String namespace, String url, List<NatureInterceptor> interceptors,
			List<NatureValidator> validators, AskType askType) {
		super();
		this.cls = cls;
		this.method = method;
		this.namespace = namespace;
		this.url = url;
		this.interceptors = interceptors;
		this.validators = validators;
		this.askType = askType;
	}
	
	public List<NatureValidator> getValidators() {
		return validators;
	}

	public AskType getAskType() {
		return askType;
	}
	
	public String toString() {
		return "CtrlBean [cls=" + cls + ", method=" + method + ", namespace=" + namespace + ", url=" + url
				+ ", interceptors=" + interceptors + ", validators=" + validators + ", askType=" + askType + "]";
	}
	
}
