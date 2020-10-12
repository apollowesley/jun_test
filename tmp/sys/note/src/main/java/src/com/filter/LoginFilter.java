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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globalClass.GlobalVariable;
import com.javasrc.Login;

public class LoginFilter extends HttpServlet implements Filter {
	private static final Logger log = LoggerFactory.getLogger(Login.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpSession session = request.getSession();
		String url = request.getServletPath();
		String contextPath = request.getContextPath();
		if (url.equals("")) {
			url += "/";
		}
		if (!detectUrl(url)) {// 此处需要另外一个函数，来过滤掉不需要过滤（验证）的url
			// 若访问后台资源滤到login
			String user = session.getAttribute("UOID") == null ? null : session.getAttribute("UOID").toString();
			// if (user == null) {// 转入首面 停止验证用户登录
			// response.sendRedirect(contextPath + "/Login.yy");
			// return;
			// }
			log.info("在LoginFilter中，用户" + user + "已经登录！");
		}
		// sRequest.setAttribute("templatePath", "template/default");
		// 这里是模板路径，页面使用el表达式获取。形式如上所示

		sRequest.setAttribute("templatePath", GlobalVariable.getWebsite_template().substring(1));
		sRequest.setAttribute("templatePathMobile", GlobalVariable.getWebsite_mobile().substring(1));
		sRequest.setAttribute("websiteTitle", GlobalVariable.getWebsite_title());
		sRequest.setAttribute("websiteName", GlobalVariable.getWebsite_name());

		// 这里是模板路径，页面使用el表达式获取。形式如上所示

		filterChain.doFilter(sRequest, sResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	/**
	 * @函数名:- detectUrl(String url)
	 * 
	 * @功能:- 检测url是否包含不需要过滤的内容
	 * 
	 * @输入参数:- 当前servlet所在路径，用request.getServletPath()获得
	 * 
	 * @返回值:- boolean (true为包含不需要过滤的内容 ，false为不包含)
	 */
	public boolean detectUrl(String url) {
		boolean dres = false;
		/* 过滤掉模板路径 */
		url = url.replace(GlobalVariable.getWebsite_mobile(), "");
		url = url.replace(GlobalVariable.getWebsite_template(), "");
		/* 过滤掉模板路径 */
		String detAbsolute = "/index,/Login.yy,/Register.yy,/RegLegalAjax.yy,/ForgetPassword.yy,/static,/jsp/register,/jsp/login.jsp,/css/,/img/,/js/,/jsp/forgetPassword,/admin,/tabbar/,/api/,*.api,/jsp/app.jsp,/jsp/code.jsp,/image/,/app/,/editor/,/exception/,/attached/";
		String[] detAs = detAbsolute.split(",");

		/* 检测是否是访问手机版本 */
		if (url.startsWith("/mobile")) {
			/* 如果url以"/mobile"开始，需要删除这一部分字段再验证 */
			url = url.substring(7);
		}
		/* 检测是否是访问手机版本 */
		for (int i = 0; i < detAs.length; i++) {
			if (detAs[i].startsWith("/") && url.startsWith(detAs[i])) {
				dres = true;
				break;
			} else if (detAs[i].startsWith("*") && url.endsWith(detAs[i].replace("*", ""))) {
				dres = true;
				break;
			}
		}
		return dres;
	}
}
