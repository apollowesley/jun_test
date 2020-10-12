package com.javasrc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;

public class Register extends RequestParameter {

	/**
	 * Constructor of the object.
	 */
	public Register() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=GBK");
		String username = getParameter("username");
		String password = getParameter("password");
		String email = getParameter("email");
		UsersDAO usersDao = new UsersDAO();
		Users user = new Users();
		List<Users> listu = usersDao.findByLoginid(username);

		if (listu.size() > 0) {
			request.setAttribute("errpos", "注册");
			request.setAttribute("errmsg", "用户名已存在");
			response.sendRedirect("../jsp/jumperror.jsp");
		} else {
			user.setLoginid(username);
			user.setUsername(username);
			user.setPassword(CipherUtil.generatePassword(password));
			if (!email.equals("")) {
				user.setEmail(email);
			}
			usersDao.save(user);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			request.setAttribute("okpos", "注册账号");
			request.setAttribute("okmsg", "注册成功");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumpok.jsp").forward(request, response);
			/*
			 * Testdatebase testdatebase = new Testdatebase();
			 * testdatebase.doSelect(request, response);
			 */
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

}
