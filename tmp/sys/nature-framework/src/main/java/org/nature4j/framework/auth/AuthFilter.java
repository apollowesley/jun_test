package org.nature4j.framework.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.util.FilterUtil;

public class AuthFilter implements Filter {
	public void destroy() {
		
	} 

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) res;
		NatureContext.setContext(request,response);
		String targetKey = request.getServletPath();
		if (targetKey.indexOf(".") != -1|| FilterUtil.isExclusion(request)) {
			chain.doFilter(request, response); 
			return;
		}  
		
		
		switch (NatureAuther.hasPrem(targetKey)) {
		case 0:
			chain.doFilter(request, response);
			break;
		case 1:
			FilterUtil.sendRedirect(response, ConfigHelper.getNoLoginUrl());
			break;
		case 2:
			FilterUtil.sendRedirect(response, ConfigHelper.getNoPremUrl());
			break;
		}
		
	} 

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
