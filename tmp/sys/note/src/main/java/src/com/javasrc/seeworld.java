package com.javasrc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.BillsDAO;
import com.staticClass.MapToJson;
import com.staticClass.TopicKeyWords;

public class seeworld extends RequestParameter {

	/**
	 * Constructor of the object.
	 */
	public seeworld() {
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
		getRecords(request, response);
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
		getRecords(request, response);
	}

	private void getRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer pageNum = new Integer(getParameter("pageNum") == null ? "0" : getParameter("pageNum"));
		/* parameter1 */
		String parameter1 = "";
		/* parameter2 */
		String parameter2 = "";
		/* 当前页不大于0时，重新设置 */
		pageNum = pageNum >= 0 ? pageNum : 0;
		/* 用来读取数据 */
		BillsDAO billsDao = new BillsDAO();
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);

		/* 显示分页数据的条数 */
		String ofTotal = billsDao.findByPropertyPagingCounts(parameter1, parameter2, pageNum, 20, "0", currentdatems1 + " 23:59:59");
		/* 总页数，使用BigDecimal的 远离0的舍入 */
		/*
		 * request.setAttribute("pages", (new BigDecimal((new Double(ofTotal)) /
		 * new Double("10"))).setScale(0, BigDecimal.ROUND_UP));
		 */
		/* 总页数 */
		int pages = (new Integer(ofTotal) - 1) / 20;
		/* 当前页大于总页时，重新设置 */
		pageNum = pageNum >= pages ? pages : pageNum;
		/* 显示分页数据 */
		List list = billsDao.findByPropertyPaging(parameter1, parameter2, pageNum, 20, "0", currentdatems1 + " 23:59:59");
		list = TopicKeyWords.addUrlLink(list);
		/* 当前页 */
		request.setAttribute("pagenow", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("ofTotal", ofTotal);
		request.setAttribute("list", list);
		String result = MapToJson.parseJson(list);
		request.setAttribute("listjson", result);
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/seeworld.jsp").forward(request, response);
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
