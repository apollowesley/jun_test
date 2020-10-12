package com.javasrc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;

public class changePwandEmail extends RequestParameter {

	/**
	 * Constructor of the object.
	 */
	public changePwandEmail() {
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
		String cpae = getParameter("cpae");
		if (cpae == null) {
			return;
		}
		if (cpae.equals("cp")) {
			doChangePassword(request, response);
		} else if (cpae.equals("ce")) {
			doChangeEmail(request, response);
		}
	}

	public void doChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UOID = request.getSession().getAttribute("UOID").toString();
		String UName = (String) request.getSession().getAttribute("UName");
		String password_old = getParameter("password_old");
		String password_new = getParameter("password_new");
		String password_new2 = getParameter("password_new2");
		if (password_new.equals(password_new2) && password_old != null && !password_old.equals("")) {
			Users user = new Users();
			UsersDAO usersDao = new UsersDAO();
			List<Users> list = usersDao.findByUsername(UName);
			if (list.size() > 0) {
				user = list.get(0);
				String password_database = user.getPassword();
				if (CipherUtil.validatePassword(password_database, password_old)) {
					user.setPassword(CipherUtil.generatePassword(password_new));
					usersDao.save(user);
				} else {
					request.setAttribute("errpos", "修改密码");
					request.setAttribute("errmsg", "旧密码不正确");
					request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("errpos", "修改密码");
				request.setAttribute("errmsg", "用户名不存在");
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
				return;
			}
		} else {
			request.setAttribute("errpos", "修改密码");
			request.setAttribute("errmsg", "两个新密码不相同");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
			return;
		}
		doReLogin(request, response);
	}

	public void doChangeEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UOID = request.getSession().getAttribute("UOID").toString();
		String UName = (String) request.getSession().getAttribute("UName");
		String password_old = getParameter("password_old");
		String username_new = getParameter("username");
		String email_new = getParameter("email");
		String tel_new = getParameter("tel");
		Users user = new Users();
		UsersDAO usersDao = new UsersDAO();
		List<Users> list = usersDao.findByUsername(UName);
		if (list.size() > 0) {
			user = list.get(0);
			String password_database = user.getPassword();
			if (CipherUtil.validatePassword(password_database, password_old)) {
				if (username_new != null && !username_new.equals("")) {
					user.setUsername(username_new);
				}
				if (email_new != null && !email_new.equals("")) {
					user.setEmail(email_new);
				}
				if (tel_new != null && !tel_new.equals("")) {
					user.setTelephone(tel_new);
				}
				usersDao.save(user);
			} else {
				request.setAttribute("errpos", "修改用户名和邮箱");
				request.setAttribute("errmsg", "旧密码不正确");
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
				return;
			}
		} else {
			request.setAttribute("errpos", "修改用户名和邮箱");
			request.setAttribute("errmsg", "用户名不存在");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
			return;
		}
		doReLogin(request, response);
	}

	public void doReLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		HttpSession session = request.getSession();
		session.invalidate();
		request.setAttribute("okpos", "修改账号信息");
		request.setAttribute("okmsg", "修改成功，网站已经退出，需要重新登录");
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumpok.jsp").forward(request, response);
		return;
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
