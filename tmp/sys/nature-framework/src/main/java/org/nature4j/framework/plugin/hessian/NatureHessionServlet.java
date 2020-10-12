package org.nature4j.framework.plugin.hessian;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature4j.framework.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.server.HessianServlet;

@WebServlet(urlPatterns="*.hessian",loadOnStartup=0)
public class NatureHessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger LOGGER = LoggerFactory.getLogger(NatureHessionServlet.class);

	public void init(ServletConfig sc) throws ServletException {
		HessianServiceHelper.init(sc);
	}

	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		LOGGER.info("====================["+DateUtil.dateTime()+"]====================");
		LOGGER.info("请求地址："+request.getMethod()+":"+request.getRequestURI());
		long beg = System.currentTimeMillis();
		
		String servletPath = request.getServletPath();
		HessianServlet hessianMap = HessianServiceHelper.getHessianMap(servletPath);
		hessianMap.service(request, response);
		
		LOGGER.info("消耗时长："+(System.currentTimeMillis()-beg)+" ms");
		LOGGER.info("=============================================================\n");
	}


}
