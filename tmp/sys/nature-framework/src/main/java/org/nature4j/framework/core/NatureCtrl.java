package org.nature4j.framework.core;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nature4j.framework.bean.Page;
import org.nature4j.framework.cache.NatureContext;

public class NatureCtrl implements Serializable {
	private static final long serialVersionUID = 1L;
	public String token = null;
	public String locale = null;
	public Page page = Page.newInstance("page");

	public HttpSession getSession() {
		return NatureContext.getRequest().getSession(false);
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
