package org.nature.framework.core;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.bean.Page;
import org.nature.framework.cache.NatureContext;
import org.nature.framework.cache.NatureSession;
import org.nature.framework.cache.SessionFactory;

public class NatureCtrl implements Serializable {
	private static final long serialVersionUID = 1L;
	public String token = null;
	public String locale = null;
	public Page page = new Page(10, 1);;

	public NatureSession getSession() {
		return new SessionFactory().init(NatureContext.getRequest()).getNatureSession();
	}

	public String getRealPath() {
		return NatureContext.getRequest().getServletContext().getRealPath("/");
	}

	public HttpServletRequest getRequest() {
		return NatureContext.getRequest(); 
	}
	
	public HttpServletResponse getResponse() {
		return NatureContext.getResponse();
	}

}
