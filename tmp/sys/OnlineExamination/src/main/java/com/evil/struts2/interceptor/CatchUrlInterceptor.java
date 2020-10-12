package com.evil.struts2.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.evil.service.RightService;
import com.evil.util.ValidateUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CatchUrlInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionProxy proxy = arg0.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if (ValidateUtil.isNull(ns) || ns.equals("/")) {
			ns = "";
		}
		String url = ns + "/" + actionName;
		ServletContext sc = ServletActionContext.getServletContext();
		WebApplicationContext ac = (WebApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(sc);
		RightService rs=(RightService) ac.getBean("rightService");
		rs.appendRightByUrl(url);
		return arg0.invoke();
	}

}
