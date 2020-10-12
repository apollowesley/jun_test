package org.durcframework.core.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.durcframework.core.PathMapper;
import org.durcframework.core.UserContext;
import org.durcframework.core.util.RequestUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;

public class UserFilter extends BaseFilter {

	private String loginUrl = "login.jsp";

	@Override
	protected boolean doMyService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			checkLogin(request);
			checkOther(request, response);
			return true;
		} catch (NoLoginException e) {
			redirect(request, response);
		} catch (OtherException e) {
			checkOtherFailHanlder(request, response);
		}
		return false;
	}

	protected void checkOther(HttpServletRequest request,
			HttpServletResponse response) throws OtherException {
		
	}
	
	protected void checkOtherFailHanlder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
	}

	/**
	 * 跳转
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	protected void redirect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 如果是ajax请求
		/*
		 * 前台页面可以这样处理 if (typeof(jQuery) != 'undefined') {
		 * $(document).ajaxError(function (event, request, settings) { if
		 * (request.getResponseHeader("X-timeout") && request.status == 401) {
		 * // 跳转到首页 top.location.href = ctx; }else{ alert("系统异常"); } }); }
		 * 这段代码放在页面底部即可
		 */
		if (RequestUtil.isAjaxRequest(request)) {
			response.setHeader("X-timeout", "1");
			response.setStatus(401);
			response.getWriter().close();
			return;
		}
		response.sendRedirect(request.getContextPath() + "/"
				+ getNeedLoginPage());
	}

	/**
	 * 用户登陆页面
	 * 
	 * @return
	 */
	protected String getNeedLoginPage() {
		return loginUrl;
	}
	
	private void checkLogin(HttpServletRequest request) throws NoLoginException {
		boolean isExcludeUrl = isExcludeUrl(request);

		if (isExcludeUrl) {
			return;
		}
		
		if(!hasUser(request)) {
			throw new NoLoginException();
		}
	}
	
	protected boolean hasUser(HttpServletRequest request) {
		return UserContext.getInstance().getUser(request.getSession()) != null;
	}

	/**
	 * 该路径是否不需要登录检测
	 * 
	 * @param request
	 * @return 返回true,不需要登录检测
	 */
	protected boolean isExcludeUrl(HttpServletRequest request) {
		return UserContext.isExcludeUrl(request);
	}
	
	@Override
	protected void initBeanWrapper(BeanWrapper bw) throws BeansException {

		String paramValue = this.getFilterConfig().getInitParameter("excludeUrl");
		String loginUrl = this.getFilterConfig().getInitParameter("loginUrl");

		if (paramValue != null) {
			String[] patterns = paramValue.split(",");

			PathMapper<Boolean> pathMapper = new PathMapper<Boolean>();

			for (String urlPattern : patterns) {
				if (urlPattern != null) {
					pathMapper.put(urlPattern.trim(), Boolean.TRUE);
				}
			}
			
			UserContext.setPathMapper(pathMapper);
		}

		if (loginUrl != null) {
			this.loginUrl = loginUrl;
		}

	
	}
	
	public class NoLoginException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	
	public class OtherException extends Exception {
		private static final long serialVersionUID = 1L;
	}
 }
