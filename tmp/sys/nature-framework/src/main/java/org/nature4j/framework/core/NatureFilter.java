package org.nature4j.framework.core;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature4j.framework.handler.Hanlder;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.util.FilterUtil;
import org.nature4j.framework.util.StringUtil;


@WebFilter(urlPatterns="/*",dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.ERROR})
public class NatureFilter implements Filter {
	public void destroy() {
		
	} 

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) res; 
		request.setCharacterEncoding("UTF-8");
		String basePath = request.getServletContext().getContextPath();
		if (StringUtil.isNotBank(ConfigHelper.getBasePath())) {
			request.setAttribute("basePath", ConfigHelper.getBasePath());
		}else {
			request.setAttribute("basePath", basePath);
		}
		String targetKey = request.getRequestURI().substring(basePath.length());
		if (targetKey.indexOf(".") != -1||FilterUtil.isExclusion(request)) {
			chain.doFilter(request, response);
			return;
		}
		if (targetKey.endsWith("/")&&targetKey.length()>1) {
			targetKey=targetKey.substring(0, targetKey.length()-1);
		}
		Hanlder.doHandler(targetKey, request, response); 
		
	}
 
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
