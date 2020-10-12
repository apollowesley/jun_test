package com.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.globalClass.GlobalVariable;
import com.hibernate.voDao.BillsDAO;
import com.hibernate.voDao.BillsImages;
import com.hibernate.voDao.BillsImagesDAO;
import com.hibernate.voDao.BillsType;
import com.hibernate.voDao.BillsTypeDAO;
import com.staticClass.MapToJson;
import com.staticClass.TopicKeyWords;

public class Bills extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Bills() {
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
		response.setContentType("text/xml; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		Object UOID = request.getSession().getAttribute("UOID");
		if (UOID == null) {
			response.sendRedirect("../");// servlet的上一页
			return;
		}
		/* 用来读取数据 */
		BillsDAO billsDao = new BillsDAO();
		String a = request.getParameter("a");
		a = a == null ? "" : a;
		if (a.equals("btlid")) {
			/* btlid为bill travel leader id,即此条记录是不是一个父记账记录 */
			List list = billsDao.findByPropertyTravelLeader("uid", request.getSession().getAttribute("UOID"));
			String result = MapToJson.parseJson(list);
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
			return;
		} else if (a.equals("btmid")) {// 这一条是ajax获取
			String btid = request.getParameter("btid");
			btid = btid == null ? "" : btid;
			/* btid为bill travel id,即此条记录的父记账记录id */
			/* btmid为bill travel member id,即此条记录是不是一个父记账记录 */
			List list = billsDao.findByPropertyTravelMember(btid);
			list = TopicKeyWords.addUrlLink(list);
			String result = MapToJson.parseJson(list);
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
			return;
		} else if (a.equals("btmidpage")) {// 这一条是链接获取后返回页面
			String btid = request.getParameter("btid");
			btid = btid == null ? "" : btid;
			/* btid为bill travel id,即此条记录的父记账记录id */
			/* btmid为bill travel member id,即此条记录是不是一个父记账记录 */
			List list = billsDao.findByPropertyTravelMember(btid);// 详细记录
			com.hibernate.voDao.Bills leader = billsDao.findById(Integer.parseInt(btid));// 父记录
			BillsType leadername = new BillsTypeDAO().findById(leader.getBtype());// 父记录
			list = TopicKeyWords.addUrlLink(list);
			String result = MapToJson.parseJson(list);
			request.setAttribute("listjson", result);
			request.setAttribute("list", list);
			request.setAttribute("leadername", leadername);
			request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/billstraveldetail.jsp").forward(request, response);
			return;
		} else if (a.equals("btype")) {// 这一条是ajax获取，用于返回当前用户的所有账单类型

			BillsTypeDAO bills_typeDao = new BillsTypeDAO();
			List list = bills_typeDao.findByUserId(request.getSession().getAttribute("UOID"));
			String result = MapToJson.parseJson(list);
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
			return;
		} else if (a.equals("breply")) {// 这一条是ajax获取，用于返回当前bid记录的回复内容
			String bid = request.getParameter("bid");
			if (bid == null) {
				PrintWriter out = response.getWriter();
				out.print("fail");
				out.close();
			} else {
				List list = billsDao.findReplyBybid(bid);
				list = TopicKeyWords.addUrlLink(list);
				String result = MapToJson.parseJson(list);
				PrintWriter out = response.getWriter();
				out.print(result);
				out.close();
			}
			return;
		} else if (a.equals("bforward")) {// 这一条是ajax获取，用于返回当前bid记录的回复内容
			String bid = request.getParameter("bid");
			if (bid == null) {
				PrintWriter out = response.getWriter();
				out.print("fail");
				out.close();
			} else {
				List list = billsDao.findForwardBybid(bid);
				list = TopicKeyWords.addUrlLink(list);
				String result = MapToJson.parseJson(list);
				PrintWriter out = response.getWriter();
				out.print(result);
				out.close();
			}
			return;
		} else if (a.equals("bid")) {// 这一条是ajax获取，用于返回当前bid记录
			String bid = request.getParameter("bid");
			if (bid == null) {
				PrintWriter out = response.getWriter();
				out.print("fail");
				out.close();
			} else {
				List list = billsDao.findBybid(bid);
				list = TopicKeyWords.addUrlLink(list);
				String result = MapToJson.parseJson(list);
				PrintWriter out = response.getWriter();
				out.print(result);
				out.close();
			}
			return;
		} else if (a.equals("searchTopic")) {// 这一条是ajax获取，用于返回当前bid记录的回复内容
			String topicname = request.getParameter("topicname");
			topicname = java.net.URLDecoder.decode(topicname, "UTF-8");
			if (topicname == null) {
				response.sendRedirect("Bills.yy?");
			} else {
				Integer pageNum = new Integer(request.getParameter("pageNum") == null ? "0" : request.getParameter("pageNum"));
				pageNum = pageNum >= 0 ? pageNum : 0;

				/* 用来读取数据 */
				/* 显示分页数据的条数 */
				/* 显示分页数据的条数 */
				String ofTotal = billsDao.findAllByTopicNameCount(topicname);
				/* 总页数 */
				int pages = (new Integer(ofTotal) - 1) / 20;
				/* 当前页大于总页时，重新设置 */
				pageNum = pageNum >= pages ? pages : pageNum;
				/* 显示分页数据 */
				List list = billsDao.findAllByTopicName(topicname, pageNum, 20);
				list = TopicKeyWords.addUrlLink(list);
				String result = MapToJson.parseJson(list);
				request.setAttribute("listjson", result);
				request.setAttribute("list", list);
				/* 当前页 */
				request.setAttribute("topicname", java.net.URLEncoder.encode(java.net.URLEncoder.encode(topicname, "UTF-8"), "UTF-8"));
				request.setAttribute("pagenow", pageNum);
				request.setAttribute("pages", pages);
				request.setAttribute("ofTotal", ofTotal);
				request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/bills.jsp").forward(request, response);
			}
			return;
		}
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);
		/* parameter */
		String parameter1 = "uid," + request.getSession().getAttribute("UOID") + ";";
		String parameter2 = "";
		/** 显示分页数据 */
		List list = billsDao.findByPropertyPaging(parameter1, parameter2, 0, 12, "0", currentdatems1 + " 23:59:59");
		list = TopicKeyWords.addUrlLink(list);
		String result = MapToJson.parseJson(list);
		request.setAttribute("listjson", result);
		request.setAttribute("list", list);
		request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/bills.jsp").forward(request, response);
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
		/* 用来存数据 */
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("a");
		if (parameter.equals("add")) {
			doAdd(request, response);
		} else if (parameter.equals("addreply")) {
			doAddreply(request, response);
		} else if (parameter.equals("addforward")) {
			doAddforward(request, response);
		} else if (parameter.equals("addtravel")) {
			doAddTravel(request, response);
		} else if (parameter.equals("modify")) {
			doModify(request, response);
		} else if (parameter.equals("delete")) {
			doDelete(request, response);
		} else if (parameter.equals("addnewbtype")) {
			doAddNewbtype(request, response);
		} else {
			response.sendRedirect("Bills.yy?");
		}
	}

	public void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String bcaption = request.getParameter("bcaption");
		TopicKeyWords.checkTopic(bcaption);
		String onlyaddmood = request.getParameter("onlyaddmood");
		if (onlyaddmood != null && onlyaddmood.equals("onlyaddmood")) {
			BillsDAO billsDao = new BillsDAO();
			com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();

			bill.setUid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
			bill.setUsername(request.getSession().getAttribute("UName").toString());
			bill.setBdate(new java.util.Date());
			bill.setBtype(28);
			bill.setBio(5);
			bill.setBamount(0);
			bill.setBcaption(bcaption);
			bill.setBctype(0);
			billsDao.save(bill);
			response.sendRedirect("Bills.yy?");
			return;
		}
		String btypename = request.getParameter("btype");// 这里前台是字符串
		Integer btype = Integer.parseInt(request.getParameter("btypename"));// 这里前台是数字
		Integer bio = Integer.parseInt(request.getParameter("bio"));
		Double bamount = Double.parseDouble(request.getParameter("bamount"));
		String travel = request.getParameter("travel");// 是否是一次行程的子行程（是则为addtravel，否则为null）
		travel = travel == null ? "" : travel;
		String theimage_form = request.getParameter("theimage_form");// 是否有图片
		theimage_form = theimage_form == null ? "" : theimage_form;
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill.setUid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		bill.setUsername(request.getSession().getAttribute("UName").toString());
		bill.setBdate(new java.util.Date());
		bill.setBtype(btype);
		bill.setBio(bio);
		bill.setBamount(bamount);
		bill.setBcaption(bcaption);
		if (!theimage_form.equals("")) {
			BillsImagesDAO billsImagesDao = new BillsImagesDAO();
			BillsImages billsImages = (BillsImages) billsImagesDao.findByBidir(theimage_form).get(0);
			bill.setBimageid(billsImages.getBiid().toString());
		}
		if (travel.equals("travel")) {
			String btid = request.getParameter("btid");// 是否是一次行程的子行程（是则为addtravel，否则为null）
			btid = btid == null ? "" : btid;
			bill.setBtid(Integer.parseInt(btid));
			bill.setBbetravelmember(true);
		}
		billsDao.save(bill);
		response.sendRedirect("Bills.yy?");
	}

	public void doAddreply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String bcaption = request.getParameter("bcaption");
		String frombid = request.getParameter("frombid");
		Integer bid = Integer.parseInt(frombid);
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();

		// 回复来自哪一条记录id,更新原始记录，评论次数+1//开始
		com.hibernate.voDao.Bills billfrom = billsDao.findById(bid);
		billfrom.setReplys(billfrom.getReplys() + 1);
		billsDao.save(billfrom);
		// 回复来自哪一条记录id,更新原始记录，评论次数+1//结束

		if (billfrom.getRootbid() == 0)// 如果来源记录的根为0，则以它为根，如果根不为0，则以此根为下一个的根
		{
			bill.setRootbid(bid);
		} else {
			bill.setRootbid(billfrom.getRootbid());
		}

		bill.setUid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		bill.setUsername(request.getSession().getAttribute("UName").toString());
		bill.setBdate(new java.util.Date());
		bill.setBtype(0);
		bill.setBio(5);
		bill.setBamount(0);
		bill.setBcaption(bcaption);
		bill.setBctype(1);
		bill.setFrombid(bid);
		billsDao.save(bill);

		/* 如果同时转发--开始 */
		String andforwad = request.getParameter("andforward");
		if (andforwad != null && andforwad.equals("true")) {
			doAddforward(request, response);
		}
		/* 如果同时转发--结束 */

		PrintWriter out = response.getWriter();
		out.print("success");
		out.close();
	}

	public void doAddforward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String bcaption = request.getParameter("bcaption");
		String frombid = request.getParameter("frombid");
		Integer bid = Integer.parseInt(frombid);
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();

		// 回复来自哪一条记录id,更新原始记录，评论次数+1//开始
		com.hibernate.voDao.Bills billfrom = billsDao.findById(bid);
		billfrom.setForwards(billfrom.getForwards() + 1);
		billsDao.save(billfrom);
		// 回复来自哪一条记录id,更新原始记录，评论次数+1//结束

		if (billfrom.getRootbid() == 0)// 如果来源记录的根为0，则以它为根，如果根不为0，则它的根为下一个的根
		{
			bill.setRootbid(bid);
		} else {
			bill.setRootbid(billfrom.getRootbid());
		}

		bill.setUid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		bill.setUsername(request.getSession().getAttribute("UName").toString());
		bill.setBdate(new java.util.Date());
		bill.setBtype(0);
		bill.setBio(5);
		bill.setBamount(0);
		bill.setBcaption(bcaption);
		bill.setBctype(2);
		bill.setFrombid(bid);
		billsDao.save(bill);

		/* 如果同时评论--开始 */
		String andreply = request.getParameter("andreply");
		if (andreply != null && andreply.equals("true")) {
			doAddreply(request, response);
		}
		/* 如果同时评论--结束 */

		PrintWriter out = response.getWriter();
		out.print("success");
		out.close();
	}

	public void doAddTravel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String btype = request.getParameter("btype");
		btype = java.net.URLDecoder.decode(btype, "UTF-8");
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill.setUid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		bill.setUsername(request.getSession().getAttribute("UName").toString());
		bill.setBdate(new java.util.Date());

		BillsTypeDAO bills_typeDao = new BillsTypeDAO();
		List list = bills_typeDao.findByNameTestIsExist("btypename", btype);
		if (list != null && !list.isEmpty() && list.size() > 0) {

		} else {
			BillsType bills_type = new BillsType();
			bills_type.setBtypename(btype);
			bills_type.setBtypeuserid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
			bills_typeDao.save(bills_type);
		}
		List<BillsType> list1 = bills_typeDao.findByNameTestIsExist("btypename", btype);
		bill.setBtype(list1.get(0).getBtypeid());
		bill.setBio(0);
		bill.setBamount(0);
		bill.setBcaption("");
		bill.setBbetravelleader(true);
		billsDao.save(bill);
		PrintWriter out = response.getWriter();
		out.print("success");
		out.close();
	}

	public void doModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int bid = Integer.parseInt(request.getParameter("b"));
		String btype = request.getParameter("btype_m");// 这里前台是字符串
		Integer bio = Integer.parseInt(request.getParameter("bio_m"));
		Double bamount = Double.parseDouble(request.getParameter("bamount_m"));
		String bcaption = request.getParameter("bcaption_m");
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill = billsDao.findById(bid);

		// bill.setBtype(0);// 需要修改
		if (bill.getUid().toString().equals(request.getSession().getAttribute("UOID").toString())) {
			bill.setBio(bio);
			bill.setBamount(bamount);
			bill.setBcaption(bcaption);
			billsDao.save(bill);
			String recordPage = request.getParameter("r");
			if (recordPage != null && recordPage.equals("record")) {
				response.sendRedirect("Records.yy?");
			} else {
				response.sendRedirect("Bills.yy?");
			}
		} else {
			request.setAttribute("errpos", "修改记录");
			request.setAttribute("errmsg", "权限错误");
			request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/jumperror.jsp").forward(request, response);
			return;
		}
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int bid = Integer.parseInt(request.getParameter("b"));
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill = billsDao.findById(bid);
		if (bill.getUid().toString().equals(request.getSession().getAttribute("UOID").toString())) {
			billsDao.delete(bill);
			String recordPage = request.getParameter("r");
			if (recordPage != null && recordPage.equals("record")) {
				response.sendRedirect("Records.yy?");
			} else {
				response.sendRedirect("Bills.yy?");
			}
		} else {
			request.setAttribute("errpos", "修改记录");
			request.setAttribute("errmsg", "权限错误");
			request.getRequestDispatcher(GlobalVariable.getWebsite_mobile() + "/jsp/jumperror.jsp").forward(request, response);
			return;
		}
	}

	public void doAddNewbtype(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String btypename = request.getParameter("btypename");
		btypename = java.net.URLDecoder.decode(btypename, "UTF-8");
		PrintWriter out = response.getWriter();
		if (docheckbtypename(btypename)) {
			out.print("hasExist");
			out.close();
			return;
		}
		BillsTypeDAO bills_typeDao = new BillsTypeDAO();
		BillsType bills_type = new BillsType();
		bills_type.setBtypename(btypename);
		bills_type.setBtypeuserid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		bills_typeDao.save(bills_type);
		out.print("success");
		out.close();
		return;
	}

	/**
	 * 检查是用户输入的账单类型 还是本来就已经存在的
	 * 
	 * @return true bills_type表中已经存在
	 * 
	 * @return false bills_type表中不存在
	 * 
	 */
	public boolean docheckbtypename(String btypename) {
		BillsTypeDAO bills_typeDao = new BillsTypeDAO();
		List list = bills_typeDao.findByNameTestIsExist("btypename", btypename);
		if (list != null && !list.isEmpty() && list.size() > 0) {
			return true;
		}
		return false;
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
