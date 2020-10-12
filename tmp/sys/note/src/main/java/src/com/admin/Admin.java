package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weiwo.pear.Pear;

import com.globalClass.GlobalVariable;
import com.hibernate.voDao.Configuration;
import com.hibernate.voDao.ConfigurationDAO;
import com.hibernate.voDao.Feedbacks;
import com.hibernate.voDao.FeedbacksDAO;
import com.hibernate.voDao.Template;
import com.hibernate.voDao.TemplateDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.javasrc.CipherUtil;
import com.javasrc.Login;

public class Admin extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(Login.class);

	/**
	 * Constructor of the object.
	 */
	public Admin() {
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		doAdmin(request, response);
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
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		doAdmin(request, response);
	}

	private void doAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			/** 第一级参数为a=action;第二级参数为o=opetate; */
			String a = request.getParameter("a");// a=action
			a = a == null ? "" : a;
			String o = request.getParameter("o");// 操作
			o = o == null ? "List" : o;
			if (a.equals("")) {
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminLeft.jsp").forward(request, response);
				return;
			}
			Class<?> c = Class.forName(Pear.getInstance().getBean(a));// 获得对象
			Object ci = c.newInstance();// 获得对象实例
			Class<?>[] pars = new Class[2];// 获得方法时的参数类型列表
			Object[] args = new Object[2];// 调用方法时的参数列表
			pars[0] = HttpServletRequest.class;
			pars[1] = HttpServletResponse.class;
			args[0] = request;
			args[1] = response;
			Method method = c.getMethod("init", pars);// 执行初始化方法
			method.invoke(ci, args);
			method = c.getMethod(o);// 执行页面调用方法
			Object result = method.invoke(ci);

			PrintWriter out = response.getWriter();// 输出json到页面
			out.append(result.toString());
			out.flush();
			// String url = request.getServletPath();

		} catch (Exception e) {
			log.info("出错了，你应该去处理一下~");
			PrintWriter out = response.getWriter();// 输出json到页面
			out.append("{\"realSucess\":\"false\",\"success\":\"true\"}");
			out.flush();
			e.printStackTrace();
		}
	}

	/**
	 * 这是过去的方法，不再使用
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-6-23 下午10:24:12
	 */
	private void pastAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		/** 第一级参数为a=action;第二级参数为o=opetate; */
		String a = request.getParameter("a");// a=action
		a = a == null ? "" : a;
		String o = request.getParameter("operate");// 操作
		o = o == null ? "" : o;
		if (!doLogin(request, response)) {/* 验证管理员登录 */
			if (a.equals("login")) {
				doLogin2(request, response);
				return;
			} else {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + GlobalVariable.getWebsite_template() + "/admin");
				return;
			}
		}
		if (a.equals("")) {
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminLeft.jsp").forward(request, response);
			return;
		}
		if (a.equals("logout")) {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("../jsp/login.jsp");
			return;
		}
		if (a.equals("users")) {
			if (o.equals("detail")) {
				int u = Integer.parseInt(request.getParameter("uid"));// 操作
				UsersDAO usersDao = new UsersDAO();
				Users user = usersDao.findById(u);
				request.setAttribute("user", user);
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminUserDetail.jsp").forward(request, response);
				return;
			}
			if (o.equals("change")) {
				int u = Integer.parseInt(request.getParameter("uid"));// 操作
				UsersDAO usersDao = new UsersDAO();
				Users user = usersDao.findById(u);
				user.setEmail(request.getParameter("email"));
				usersDao.save(user);
				request.setAttribute("user", user);
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminUserDetail.jsp").forward(request, response);
				return;
			}
			if (o.equals("delete")) {
				int u = Integer.parseInt(request.getParameter("uid"));// 操作
				UsersDAO usersDao = new UsersDAO();
				Users user = usersDao.findById(u);
				usersDao.delete(user);
			}
			UsersDAO usersDao = new UsersDAO();
			List<Users> list = usersDao.findAll();
			request.setAttribute("list", list);
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminUser.jsp").forward(request, response);
			return;
		}
		if (a.equals("feedback")) {
			if (o.equals("detail")) {
				int f = Integer.parseInt(request.getParameter("fid"));// 操作
				FeedbacksDAO feedbacksDao = new FeedbacksDAO();
				Feedbacks feedback = feedbacksDao.findById(f);
				request.setAttribute("feedback", feedback);
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminFeedbackDetail.jsp").forward(request, response);
				return;
			}
			if (o.equals("change")) {
				int u = Integer.parseInt(request.getParameter("fid"));// 操作
				FeedbacksDAO feedbacksDao = new FeedbacksDAO();
				Feedbacks feedback = feedbacksDao.findById(u);
				feedback.setrcontents(request.getParameter("rcontents"));
				feedback.setReplystatus(true);
				feedbacksDao.save(feedback);
				request.setAttribute("feedback", feedback);
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminFeedbackDetail.jsp").forward(request, response);
				return;
			}
			if (o.equals("delete")) {
				int f = Integer.parseInt(request.getParameter("fid"));// 操作
				FeedbacksDAO feedbacksDao = new FeedbacksDAO();
				Feedbacks feedback = feedbacksDao.findById(f);
				feedbacksDao.delete(feedback);
			}
			FeedbacksDAO feedbacksDao = new FeedbacksDAO();
			List<Feedbacks> list = feedbacksDao.findAll();
			request.setAttribute("list", list);
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminFeedback.jsp").forward(request, response);
			return;
		}
		if (a.equals("template")) {
			if (o.equals("add")) {
				String tname = request.getParameter("tname");
				String tdirectory = request.getParameter("tdirectory");
				String tcopyright = request.getParameter("tcopyright");
				String ttype = request.getParameter("ttype");
				short ttypeint = Short.parseShort(ttype);
				if (tname == null || tname.isEmpty()) {
					request.setAttribute("msgpos", "添加模板");
					request.setAttribute("msgcontent", "模板名称不能为空");
					request.setAttribute("msgjump", "Admin.yy?a=template");
					request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/jumpmsg.jsp").forward(request, response);
					return;
				}
				if (tdirectory == null || tdirectory.isEmpty()) {
					request.setAttribute("msgpos", "添加模板");
					request.setAttribute("msgcontent", "模板目录不能为空");
					request.setAttribute("msgjump", "Admin.yy?a=template");
					request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/jumpmsg.jsp").forward(request, response);
					return;
				}
				TemplateDAO templateDao = new TemplateDAO();
				Template template = new Template();
				template.setTname(tname);
				template.setTdirectory(tdirectory);
				template.setTcopyright(tcopyright);
				template.setTtype(ttypeint);
				templateDao.save(template);
			}
			if (o.equals("delete")) {

			}
			if (o.equals("setdefault")) {
				String tdirectory = request.getParameter("tdirectory");// 目录
				ConfigurationDAO configurationDao = new ConfigurationDAO();
				Configuration configuration = configurationDao.findById("webDefaultTemplate");
				configuration.setCvalue(tdirectory);
				configurationDao.save(configuration);// 保存
				GlobalVariable.setWebsite_template(tdirectory);// 设置即时生效
				request.setAttribute("msgpos", "修改默认模板");
				request.setAttribute("msgcontent", "修改成功");
				request.setAttribute("msgjump", "Admin.yy?a=template");
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/jumpmsg.jsp").forward(request, response);
				return;
			}
			if (o.equals("setmobiledefault")) {
				String tdirectory = request.getParameter("tdirectory");// 目录
				ConfigurationDAO configurationDao = new ConfigurationDAO();
				Configuration configuration = configurationDao.findById("mobileDefaultTemplate");
				configuration.setCvalue(tdirectory);
				configurationDao.save(configuration);// 保存
				GlobalVariable.setWebsite_mobile(tdirectory);// 设置即时生效
				request.setAttribute("msgpos", "修改默认模板");
				request.setAttribute("msgcontent", "修改成功");
				request.setAttribute("msgjump", "Admin.yy?a=template");
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/jumpmsg.jsp").forward(request, response);
				return;
			}
			TemplateDAO templateDao = new TemplateDAO();
			List<Template> list = templateDao.findAll();
			request.setAttribute("list", list);
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/template.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminLeft.jsp").forward(request, response);
	}

	/** 验证管理员登录 */
	private Boolean doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String usera = session.getAttribute("UOIDA") == null ? null : session.getAttribute("UOIDA").toString();
		if (usera == null) {// 转入管理员登陆页面
			return false;
		} else {
			return true;
		}
	}

	/** 管理员登录 */
	private Boolean doLogin2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UsersDAO usersDao = new UsersDAO();
		List<Users> list = usersDao.findByUsername(username);

		if (list.size() > 0) {
			if (!list.get(0).getAdminid()) {
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + GlobalVariable.getWebsite_template() + "/admin");
				return false;
			}
			String password_database = list.get(0).getPassword();
			if (CipherUtil.validatePassword(password_database, password)) {
				request.getSession().setAttribute("UOIDA", list.get(0).getUid());
				request.getSession().setAttribute("UNameA", list.get(0).getUsername());
				request.getSession().setAttribute("UEmailA", list.get(0).getEmail() == null ? "" : list.get(0).getEmail());
				request.getSession().setMaxInactiveInterval(3600);
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/admin/adminLeft.jsp").forward(request, response);
				return true;
				/*
				 * Testdatebase testdatebase = new Testdatebase();
				 * testdatebase.doSelect(request, response);
				 */
			} else {
				request.setAttribute("errpos", "登录");
				request.setAttribute("errmsg", "密码错误");
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
				return false;
			}
		} else {
			request.setAttribute("errpos", "登录");
			request.setAttribute("errmsg", "用户名错误");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
			return false;
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
