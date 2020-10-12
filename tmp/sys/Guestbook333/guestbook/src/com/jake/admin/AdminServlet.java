package com.jake.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jake.bean.Message;
import com.jake.dao.AdminDao;
import com.jake.dao.DBC;
import com.jake.util.StringUtil;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void rep(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String repcontent = request.getParameter("repcontent");
		if (StringUtil.isEmpty(repcontent) && StringUtil.isEmpty(id)) {
			AdminDao ad = new AdminDao();
			DBC dbc = new DBC();
			Message mess;
			try {
				mess = dbc.getMessById(Integer.parseInt(id));
				mess.setRepcontent(repcontent);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String reptime = sdf.format(date);
				mess.setReptime(reptime);
				mess.setId(Integer.parseInt(id));
				ad.updateMess(mess);
				response.sendRedirect("/guestbook01/admin/secure/list.jsp");
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			session.setAttribute("error", "请填写后再提交，谢谢");
			response.sendRedirect("/guestbook01/admin/loginError");
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminDao ad = new AdminDao();
		String id = request.getParameter("id");
		if (id != null) {
			try {
				ad.deleteMess(Integer.parseInt(id));
				response.sendRedirect("/guestbook01/admin/secure/list.jsp");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		if (StringUtil.isEmpty(name) && StringUtil.isEmpty(title)
				&& StringUtil.isEmpty(content) && StringUtil.isEmpty(qq)
				&& StringUtil.isEmpty(email)) {
			DBC dbc = new DBC();
			Message mess;
			try {
				mess = dbc.getMessById(Integer.parseInt(id));
				mess.setName(name);
				mess.setTitle(title);
				mess.setContent(content);
				mess.setQq(qq);
				mess.setEmail(email);
				mess.setId(Integer.parseInt(id));
				AdminDao ad = new AdminDao();
				ad.updateMess(mess);
				response.sendRedirect("/guestbook01/admin/secure/list.jsp");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			session.setAttribute("error", "请填写完整，谢谢");
			response.sendRedirect("/guestbook01/admin/loginError");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String q = request.getParameter("q");
		if (q == null || q.equals("login")) {
			login(request, response);
		} else if (q.equals("edit")) {
			edit(request, response);
		} else if (q.equals("delete")) {
			delete(request, response);
		} else if (q.equals("rep")) {
			rep(request, response);
		} else {
			session.removeAttribute("admin.adminname");
			response.sendRedirect("/guestbook01/admin/login.jsp");
		}
		out.flush();
		out.close();
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String adminname = request.getParameter("adminname");
		String adminpass = request.getParameter("adminpass");
		if (StringUtil.isEmpty(adminname) && StringUtil.isEmpty(adminpass)) {
			AdminDao ad = new AdminDao();
			try {
				if (ad.validate(adminname, adminpass)) {
					session.setAttribute("admin.adminname", adminname);
					response.sendRedirect("/guestbook01/admin/secure/list.jsp");
				} else {
					session.setAttribute("error", "账号或密码错误，请重新登陆");
					response.sendRedirect("/guestbook01/admin/loginError.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			session.setAttribute("error", "账号或密码错误，请重新登陆");
			try {
				response.sendRedirect("/guestbook01/admin/loginError.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
