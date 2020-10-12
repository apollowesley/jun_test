package com.pyy.intercepter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BaseInterceptor extends HandlerInterceptorAdapter {
	private Logger log = LoggerFactory.getLogger(BaseInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.debug("NO.1 base interceptor...");
		String context = request.getContextPath();
		if ("/".equals(context)) {
			context = "";
		}
		request.setAttribute("ctx", context);
		
		/*String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		request.setAttribute("basePath", basePath);
		log.debug("basePath : " + basePath);*/
		
		
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		return super.preHandle(request, response, handler);
	}
	
}
