package com.javasrc;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.staticClass.NetworkUtil;

public class Login extends RequestParameter {
	private static final Logger log = LoggerFactory.getLogger(Login.class);

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
		String logout = getParameter("a");
		logout = logout == null ? "null" : logout;
		if (logout.equals("logout")) {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			HttpSession session = request.getSession();
			session.invalidate();
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookies[i].setMaxAge(1);// 貌似0是关闭浏览器才失效，所以用1
					cookies[i].setValue("-1");// 用于标记cookies已经过期，只是因为服务器获取不到时间
					response.addCookie(cookies[i]);
				}
			}
		}
		// request.logout();
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/login.jsp").forward(request, response);
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
		String username = getParameter("username");
		String password = getParameter("password");
		String rememberId = getParameter("rememberId");
		String UOID = (String) request.getAttribute("UOID");

		log.info("用户尝试使用" + username + " " + password + "登录");
		NetworkUtil.getIpAddr(request);

		if (UOID == null) {
			UsersDAO usersDao = new UsersDAO();
			List<Users> list = usersDao.findByLoginid(username);

			if (list.size() > 0) {
				String password_database = list.get(0).getPassword();
				if (CipherUtil.validatePassword(password, username + password_database)) {
					request.getSession().setAttribute("UOID", list.get(0).getUid());
					request.getSession().setAttribute("UName", list.get(0).getUsername());
					request.getSession().setAttribute("UEmail", list.get(0).getEmail() == null ? "" : list.get(0).getEmail());
					request.getSession().setAttribute("TEL", list.get(0).getTelephone() == null ? "" : list.get(0).getTelephone());
					request.getSession().setAttribute("Adminid", list.get(0).getAdminid());
					if (rememberId == null) {
						log.info("用户尝试使用" + username + "登录成功，会话3600秒过期");
						request.getSession().setMaxInactiveInterval(3600);
					} else {
						log.info("用户尝试使用" + username + "登录成功，会话不自动过期");
						request.getSession().setMaxInactiveInterval(-1);

						Cookie cookie = new Cookie("cookie_user", URLEncoder.encode(username, "UTF-8") + "-" + password + "-" + rememberId);
						cookie.setMaxAge(2500000); // cookie 保存一个周期
						response.addCookie(cookie);

					}
					log.info("在Login中，用户" + request.getSession().getAttribute("UOID") + "已经登录！");
					response.sendRedirect("Bills.yy");
				} else {
					request.setAttribute("errpos", "登录");
					request.setAttribute("errmsg", "密码错误");
					request.getRequestDispatcher(

					GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("errpos", "登录");
				request.setAttribute("errmsg", "用户名错误");
				request.getRequestDispatcher(

				GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
				return;
			}
		} else {
			response.sendRedirect("Bills.yy");
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
