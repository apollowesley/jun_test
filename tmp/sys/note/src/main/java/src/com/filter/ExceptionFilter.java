package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionFilter extends HttpServlet implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		try {
			filterChain.doFilter(sRequest, sResponse);
		} catch (Exception e) {
			try {
				request.getRequestDispatcher("/exception/404.jsp").forward(request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
