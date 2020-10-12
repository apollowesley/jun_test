package com.feedbacks;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.globalClass.GlobalVariable;
import com.hibernate.voDao.Feedbacks;
import com.hibernate.voDao.FeedbacksDAO;

public class Feedback extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Feedback() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		doFeedback(request, response);
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		doFeedback(request, response);
	}

	private void doFeedback(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FeedbacksDAO feedbacksDao = new FeedbacksDAO();
		Feedbacks feedback = new Feedbacks();
		if (request.getParameter("a") != null
				&& request.getParameter("a").equals("feed")) {
			String feedbackc = request.getParameter("feedbackc");
			feedback.setUid(Integer.parseInt(request.getSession()
					.getAttribute("UOID").toString()));
			feedback.setUsername(request.getSession().getAttribute("UName")
					.toString());
			feedback.setContents(feedbackc);
			feedback.setrcontents("");
			feedback.setFdate(new java.util.Date());
			feedbacksDao.save(feedback);
			return;
		}
		/* 总页数 */
		String ofTotal = feedbacksDao.findAllByPageCounts();
		int pages = (new Integer(ofTotal) - 1) / 20;
		/* 当前页 */
		Integer pageNum = new Integer(
				request.getParameter("pageNum") == null ? "0"
						: request.getParameter("pageNum"));
		/* 当前页大于总页时，重新设置 */
		pageNum = pageNum >= pages ? pages : pageNum;
		List list = feedbacksDao.findAllByPage(pageNum, 20);
		request.setAttribute("list", list);
		request.setAttribute("pagenow", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("ofTotal", ofTotal);
		request.setAttribute("list", list);
		request.getRequestDispatcher(
				GlobalVariable.getWebsite_template() + "/feedbacks/index.jsp")
				.forward(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
