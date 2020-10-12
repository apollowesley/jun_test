package com.kiss.jbpm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kiss.jbpm.exception.JBPMException;
import com.kiss.jbpm.service.IJbpmService;
import com.kiss.jbpm.utils.BeanUtils;
public class HumanTaskStartupServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void init() throws ServletException {
		super.init();
		try {
			IJbpmService jbpmService = (IJbpmService) BeanUtils.getBean("jbpmService");
			jbpmService.initMinaServer();
		}catch (Exception e) {
			throw new JBPMException("jbpm用户组初始化失败", e);
		}
	}
	
	public void destroy() {
		super.destroy();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}																						

	protected void doPost(HttpServletRequest reqquest,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(1001, "POST Method Not Allowed Here");
	}
}
