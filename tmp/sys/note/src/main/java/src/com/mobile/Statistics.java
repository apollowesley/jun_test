package com.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.StatisticsSQLResult;
import com.hibernate.voDao.BillsDAO;

public class Statistics extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Statistics() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		statistics(request, response);
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
		response.setCharacterEncoding("UTF-8");
		statistics(request, response);
	}

	/**
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
	private void statistics(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String bio = request.getParameter("bio");
		String uid = request.getSession().getAttribute("UOID").toString();
		PrintWriter out = response.getWriter();
		BillsDAO billsDao = new BillsDAO();
		List Listr = billsDao.findByDateStatistics(startDate, endDate, bio, uid);
		List<StatisticsSQLResult> list = Listr;
		/* 将返回结果存为xml格式，在页面使用js遍历再存为数组，如果这里直接将数据存为文本，在页面不易分离 ，纵然js是弱变量，也不太好做 */
		StringBuffer sb = new StringBuffer();
		sb.append("<data>");
		for (int i = 0; i < list.size(); i++) {
			sb.append("<name>" + list.get(i).getDay().getTime() + "</name>");
			sb.append("<value>" + list.get(i).getAmount() + "</value>");
		}
		// sb.append("<type_name>"+action+"</type_name>");
		sb.append("</data>");
		out.write(sb.toString());// 注意这里向jsp输出的流，在script中的截获方法
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
