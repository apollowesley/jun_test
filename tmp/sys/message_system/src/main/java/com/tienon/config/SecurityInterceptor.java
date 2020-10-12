package com.tienon.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	private static String name;
	public static String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession();
		//判断该session是否有该用户的登陆信息
		if(session.getAttribute("username")!=null) {
			this.setName((String)session.getAttribute("username"));
			return true;
		}
		response.sendRedirect("/login.html");
		return false;
	}
	
}
