package org.durcframework.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public enum WebContext {
	INSTANCE;
	
	private ThreadLocal<HttpServletRequest> threadRequest = new ThreadLocal<HttpServletRequest>();
	
	public static WebContext getInstance() {
		return INSTANCE;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.threadRequest.set(request);
	}
	
	public HttpServletRequest getRequest() {
		HttpServletRequest req = threadRequest.get();
		if(req == null) {
			req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return req;
	}
	
	/**
	 * 等同session.setAttribute(key, value);
	 * @param key
	 * @param value
	 */
	public void setAttr(String key,Object value){
		this.getSession().setAttribute(key, value);
	}
	
	/**
	 * 等同session.getAttribute(key);
	 * @param key
	 * @return
	 */
	public Object getAttr(String key){
		return this.getSession().getAttribute(key);
	}
	
	public HttpSession getSession() {
		if(getRequest() != null) {
			return getRequest().getSession();
		}
		return null;
	}
	
}
