/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
		String excludedUriStr = config.getInitParameter("excludedUris");
		if(excludedUriStr != null){
			excludedUris = excludedUriStr.split(",");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		String url = xssRequest.getServletPath();
		if (isExcludedUri(url)){
	        chain.doFilter(request, response);
	    }else {
	        chain.doFilter(xssRequest, response);
	    }
	}

	@Override
	public void destroy() {
	}

	private String[] excludedUris;
	
	private boolean isExcludedUri(String uri) {
      if (excludedUris == null || excludedUris.length <= 0) {
         return false;
      }
      for (String ex : excludedUris) {
         uri = uri.trim();
         ex = ex.trim();
         if (uri.toLowerCase().matches(ex.toLowerCase().replace("*",".*")))
            return true;
      }
      return false;
   }
	
}