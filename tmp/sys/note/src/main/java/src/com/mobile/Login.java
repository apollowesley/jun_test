package com.mobile;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globalClass.GlobalVariable;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.javasrc.CipherUtil;

public class Login extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(Login.class);

	/**
	 * Constructor of the object.
	 */
	public Login() {
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
		String logout = request.getParameter("a");
		logout = logout == null ? "null" : logout;
		if (logout.equals("logout")) {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			HttpSession session = request.getSession();
			session.invalidate();
		}
		request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/index.jsp").forward(request, response);
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
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String UOID = (String) request.getAttribute("UOID");

		if (UOID == null) {
			UsersDAO usersDao = new UsersDAO();
			List<Users> list = usersDao.findByUsername(username);

			if (list.size() > 0) {
				String password_database = list.get(0).getPassword();
				if (CipherUtil.validatePassword(password_database, password)) {
					request.getSession().setAttribute("UOID", list.get(0).getUid());
					request.getSession().setAttribute("UName", list.get(0).getUsername());
					request.getSession().setAttribute("UEmail", list.get(0).getEmail() == null ? "" : list.get(0).getEmail());
					request.getSession().setMaxInactiveInterval(3600);
					log.info("在Login中，用户" + request.getSession().getAttribute("UOID") + "已经登录！");
					response.sendRedirect("Bills.yy?");
				} else {
					request.setAttribute("errpos", "登录");
					request.setAttribute("errmsg", "密码错误");
					request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/jumperror.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("errpos", "登录");
				request.setAttribute("errmsg", "用户名错误");
				request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/jumperror.jsp").forward(request, response);
				return;
			}
		} else {
			response.sendRedirect("Bills.yy?");
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
