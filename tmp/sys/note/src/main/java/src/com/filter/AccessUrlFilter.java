package com.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 借此过滤器过确定Android客户端访问的url链接
 * 
 * 
 * @author peaches
 */
public class AccessUrlFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(AccessUrlFilter.class);

	FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("").getPath()
		// + "log4j.properties");
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
		filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest sRequest = (HttpServletRequest) request;
		log.info("Platform:billing");
		log.info("Address:" + sRequest.getRequestURL());
		// ergodic(request);
		chain.doFilter(request, response);
	}

	/**
	 * 遍历输出request
	 * 
	 * @param request
	 * @createTime 2014-11-12 下午10:06:47
	 */
	public void ergodic(ServletRequest request) {
		Enumeration rnames = request.getParameterNames();

		for (Enumeration e = rnames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			log.info(thisName + "-------" + thisValue);
		}
	}
}
