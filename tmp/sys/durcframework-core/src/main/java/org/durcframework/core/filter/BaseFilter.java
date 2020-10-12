package org.durcframework.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.durcframework.core.WebContext;
import org.springframework.web.filter.OncePerRequestFilter;

public class BaseFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException{
		this.setRequest(request);
		boolean success = this.doMyService(request, response);
		if(success) {
			filterChain.doFilter(request, response);
		}
	}
	
	protected void setRequest(HttpServletRequest request) {
		WebContext.getInstance().setRequest(request);
	}
	
	protected boolean doMyService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		return true;
	}
}
