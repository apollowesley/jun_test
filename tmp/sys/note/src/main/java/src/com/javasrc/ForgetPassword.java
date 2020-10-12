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

public class ForgetPassword extends RequestParameter {

	/**
	 * Constructor of the object.
	 */
	public ForgetPassword() {
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
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", -1);
		response.setHeader("Pragma", "no-cache");

		request.setCharacterEncoding("UTF-8");
		String email = getParameter("email");
		String password = AutoGeneratePassword.GeneratePassword();
		Users user = new Users();
		UsersDAO usersDao = new UsersDAO();
		List<Users> list = usersDao.findByEmail(email);
		if (list.size() > 0) {
			user = list.get(0);
			user.setPassword(CipherUtil.generatePassword(password));
			usersDao.save(user);
			sendEmail.send(email, password);
			request.setAttribute("okpos", "找回密码");
			request.setAttribute("okmsg", "系统已经发送一封邮件到您输入的邮箱，请您登录邮箱查看");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumpok.jsp").forward(request, response);
			return;
		}
		request.setAttribute("okpos", "找回密码");
		request.setAttribute("okmsg", "您输入的邮箱不存在");
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumpok.jsp").forward(request, response);
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
