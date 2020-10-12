package org.nature.framework.cache;

import javax.servlet.http.HttpServletRequest;

public class SessionFactory {
	HttpServletRequest request;
	public SessionFactory init(HttpServletRequest request){
		this.request = request;
		return this;
	}
	
	public NatureSession getNatureSession(){
		return new HttpNativeSession(request.getSession());
	}
}
