package com.javasrc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;

public class Testdatebase extends RequestParameter {

	private final Configuration cfg = new Configuration().configure();
	private Session session;
	private Transaction tx;
	private final UsersDAO usersDao = new UsersDAO();
	private Users user = new Users();

	/**
	 * Constructor of the object.
	 */
	public Testdatebase() {
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
		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter(); response.setCharacterEncoding("utf-8");
		 * response.setContentType("text/html; charset=utf-8"); out.println(
		 * "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		 * out.println("<HTML>");
		 * out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		 * out.println("  <BODY>"); out.print("    This is ");
		 * out.print(this.getClass()); out.println(", using the GET method");
		 * out.println("  </BODY>"); out.println("</HTML>"); out.flush();
		 * out.close();
		 */
		doSelect(request, response);
		// doDelete(request, response);
	}

	public void doSave() {

		//
		// //注意这一行，这是本文重点讨论研究的地方。
		// Configuration cfg = new AnnotationConfiguration();
		// session = cfg.buildSessionFactory().openSession();
		// tx = session.beginTransaction();
		user.setIdentityCardNumber("1");
		user.setUsername("a12");
		user.setPassword("1");
		user.setRealname("姓名！");
		usersDao.save(user);
		// session.save(user);
		// tx.commit();
		// session.close();
	}

	public void doSelect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		session = cfg.buildSessionFactory().openSession();
		tx = session.beginTransaction();
		List list = usersDao.findAll();
		tx.commit();
		session.close();
		request.setAttribute("list", list);
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/result.jsp").forward(request, response);
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		user = usersDao.findById(((Users) usersDao.findByUsername("a12").get(0)).getUid());
		usersDao.delete(user);
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
