package com.javasrc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.globalInterface.RequestParameter;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;

public class RegLegalAjax extends RequestParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3566699993464114574L;

	// private static final String CONTENT_TYPE =
	// "text/xml; charset=UTF-8";//这里最好统一用UTF-8进行编码
	@Override
	public void init() throws ServletException {
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 以下两句为取消在本地的缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		String p = getParameter("p");
		String username = null, email = null;
		if (p.equals("u")) {
			username = getParameter("username");
			username = java.net.URLDecoder.decode(username, "UTF-8");
			try {
				UsersDAO usersDao = new UsersDAO();
				List<Users> list = usersDao.findByProperty("username", username);
				StringBuffer sb = new StringBuffer();
				response.setContentType("text/xml"); // 像这样设置返回的类型。
				sb.append("<?xml version='1.0' encoding='utf-8'?>");
				sb.append("<type>");
				if (list.size() > 0) {
					sb.append("<type_name>" + list.get(0).getUsername() + "</type_name>");
				}
				// sb.append("<type_name>"+action+"</type_name>");
				sb.append("</type>");
				out.write(sb.toString());// 注意这里向jsp输出的流，在script中的截获方法
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (p.equals("e")) {
			email = getParameter("email");
			email = java.net.URLDecoder.decode(email, "UTF-8");
			try {
				UsersDAO usersDao = new UsersDAO();
				List<Users> list = usersDao.findByProperty("email", email);
				StringBuffer sb = new StringBuffer();
				response.setContentType("text/xml"); // 像这样设置返回的类型。
				sb.append("<?xml version='1.0' encoding='utf-8'?>");
				sb.append("<type>");
				if (list.size() > 0) {
					sb.append("<type_name>" + list.get(0).getEmail() + "</type_name>");
				}
				// sb.append("<type_name>"+action+"</type_name>");
				sb.append("</type>");
				out.write(sb.toString());// 注意这里向jsp输出的流，在script中的截获方法
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// username = java.net.URLDecoder.decode(username, "UTF-8");

	}
}
