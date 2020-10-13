/**
 *
 */
package com.laycms;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.laycms.config.SystemConfig;

/**
 * @author <p>Innate Solitary 于 2013-3-12 下午4:54:15</p>
 *
 */
public class ExceptionFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	private ApplicationContext context = null;
	private SystemConfig sysCfg;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		sysCfg = (SystemConfig) context.getBean("systemConfig");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
	        chain.doFilter(request, response);
        } catch (Exception e) {
        	Throwable cause = e;
        	do {
        		if(cause.getCause() != null) {
        			cause = cause.getCause();
        		} else {
        			break;
        		}
        	} while (true);
			throw new ServletException(e);
        }
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		context = null;
		sysCfg = null;
	}

}
