package com.javasrc;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.BillsDAO;
import com.hibernate.voDao.BillsImages;
import com.hibernate.voDao.BillsImagesDAO;
import com.hibernate.voDao.BillsType;
import com.hibernate.voDao.BillsTypeDAO;
import com.staticClass.Const;
import com.staticClass.MapToJson;
import com.staticClass.TopicKeyWords;

public class Bills extends RequestParameter {

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
		Object UOID = request.getSession().getAttribute("UOID");
		if (UOID == null) {
			response.sendRedirect("../");// servlet的上一页
			return;
		}
		/* 用来读取数据 */
		BillsDAO billsDao = new BillsDAO();
		String a = getParameter("a");
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
			String btid = getParameter("btid");
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
			String btid = getParameter("btid");
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
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/billstraveldetail.jsp").forward(request, response);
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
			String bid = getParameter("bid");
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
			String bid = getParameter("bid");
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
			String bid = getParameter("bid");
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
			String topicname = getParameter("topicname");
			topicname = java.net.URLDecoder.decode(topicname, "UTF-8");
			if (topicname == null) {
				response.sendRedirect("Bills.yy?");
			} else {
				Integer pageNum = new Integer(getParameter("pageNum") == null ? "0" : getParameter("pageNum"));
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
				request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/searchTopic.jsp").forward(request, response);
			}
			return;
		}
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);
		/* parameter */
		String parameter1 = "";
		String parameter2 = "";
		String parameter3 = "(uid='" + UOID + "' or uid in(select user_id_1 from relationship_friends where user_id_2='" + UOID
				+ "' and status='2') or uid in(select user_id_2 from relationship_friends where user_id_1='" + UOID + "' and status='2')) and ";
		/** 显示分页数据 */
		int size = 12;
		if (request.getParameter("print") != null) {
			size = 100000000;
		}
		List list = billsDao.findByPropertyPaging(parameter1, parameter2, parameter3, 0, size, "0", currentdatems1 + " 23:59:59");
		list = TopicKeyWords.addUrlLink(list);
		/** 获取显示多图片 开始 **/
		dealMultiImage(list);
		/** 获取显示多图片 结束 **/

		String result = MapToJson.parseJson(list);
		request.setAttribute("listjson", result);
		request.setAttribute("list", list);
		if (request.getParameter("print") != null) {
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/print.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/bills.jsp").forward(request, response);
		}
	}

	private void dealMultiImage(List list) {
		/** 获取显示多图片 开始 **/
		if (Const.ListIsNotBlank(list)) {
			String sqlTemp = "select t.bidir,'%s' bid,t.biid  from bills_images t where t.biid in(%s) ";
			List listsql = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				String imageIds = "";
				imageIds = Const.StringToString(((Map) list.get(i)).get("bimageid"));
				if (Const.stringIsBlank(imageIds)) {
					continue;
				}
				imageIds = "'" + imageIds.replace(",", "','") + "'";
				String sql = String.format(sqlTemp, ((Map) list.get(i)).get("bid"), imageIds);
				listsql.add(sql);
			}
			String sql = Const.ArraysToString(listsql.toArray(), " union ");
			if (Const.stringIsBlank(sql)) {
				return;
			}
			List listImages = generalDao.getList("select * from (" + sql + ") a order by biid ");
			if (Const.ListIsNotBlank(listImages)) {
				for (int i = 0; i < list.size(); i++) {
					String bidt = ((Map) list.get(i)).get("bid").toString();
					String value = "";
					String values = "";
					for (int j = 0; j < listImages.size(); j++) {
						Object[] o = (Object[]) listImages.get(j);
						if (bidt.equals(o[1])) {
							if (value.length() == 0) {
								value = (String) o[0];
							}
							values += o[0] + "|";
						}
					}
					((Map) list.get(i)).put("bidir", value.length() == 0 ? "null" : value);// 单个，用于支持旧版本
					((Map) list.get(i)).put("bidirs", values);// 多个
				}
			}
		}
		/** 获取显示多图片 结束 **/
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
		String parameter = getParameter("a");
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
		String bcaption = getParameter("bcaption");
		TopicKeyWords.checkTopic(bcaption);
		String onlyaddmood = getParameter("onlyaddmood");
		String theimage_form = getParameter("theimage_form");// 是否有图片
		theimage_form = theimage_form == null ? "" : theimage_form;
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
			if (!theimage_form.equals("")) {
				BillsImagesDAO billsImagesDao = new BillsImagesDAO();
				BillsImages billsImages = (com.hibernate.voDao.BillsImages) billsImagesDao.findByBidir(theimage_form).get(0);
				bill.setBimageid(billsImages.getBiid().toString());
			}
			billsDao.save(bill);
			response.sendRedirect("Bills.yy?");
			return;
		}
		String btypename = getParameter("btype");// 这里前台是字符串
		Integer btype = Integer.parseInt(getParameter("btypename"));// 这里前台是数字
		Integer bio = Integer.parseInt(getParameter("bio"));
		Double bamount = Double.parseDouble(getParameter("bamount"));
		String travel = getParameter("travel");// 是否是一次行程的子行程（是则为addtravel，否则为null）
		travel = travel == null ? "" : travel;
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill.setUid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		bill.setUsername(request.getSession().getAttribute("UName").toString());
		bill.setBdate(new java.util.Date());
		bill.setBtype(btype);
		bill.setBio(bio);
		bill.setBamount(bamount);
		bill.setBcaption(bcaption);

		if (!Const.stringIsEmpty(theimage_form)) {
			String[] ss = theimage_form.split("|");// 后台支持上传多张图片
			for (int i = 0; i < ss.length; i++) {
				BillsImagesDAO billsImagesDao = new BillsImagesDAO();
				BillsImages billsImages = (BillsImages) billsImagesDao.findByBidir(ss[i]).get(0);
				bill.setBimageid(bill.getBimageid() + "," + billsImages.getBiid().toString());
			}
		}

		if (travel.equals("travel")) {
			String btid = getParameter("btid");// 是否是一次行程的子行程（是则为addtravel，否则为null）
			btid = btid == null ? "" : btid;
			bill.setBtid(Integer.parseInt(btid));
			bill.setBbetravelmember(true);
		}
		billsDao.save(bill);
		response.sendRedirect("Bills.yy?");
	}

	public void doAddreply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String bcaption = getParameter("bcaption");
		String frombid = getParameter("frombid");
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
		String andforwad = getParameter("andforward");
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
		String bcaption = getParameter("bcaption");
		String frombid = getParameter("frombid");
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
		String andreply = getParameter("andreply");
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
		String btype = getParameter("btype");
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
		int bid = Integer.parseInt(getParameter("b"));
		String btype = getParameter("btype_m");// 这里前台是字符串
		Integer bio = Integer.parseInt(getParameter("bio_m"));
		Double bamount = Double.parseDouble(getParameter("bamount_m"));
		String bcaption = getParameter("bcaption_m");
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill = billsDao.findById(bid);

		// bill.setBtype(0);// 需要修改
		if (bill.getUid().toString().equals(request.getSession().getAttribute("UOID").toString())) {
			bill.setBio(bio);
			bill.setBamount(bamount);
			bill.setBcaption(bcaption);
			billsDao.save(bill);
			String recordPage = getParameter("r");
			if (recordPage != null && recordPage.equals("record")) {
				response.sendRedirect("Records.yy");
			} else {
				response.sendRedirect("Bills.yy");
			}
		} else {
			request.setAttribute("errpos", "修改记录");
			request.setAttribute("errmsg", "权限错误");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
			return;
		}
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int bid = Integer.parseInt(getParameter("b"));
		BillsDAO billsDao = new BillsDAO();
		com.hibernate.voDao.Bills bill = new com.hibernate.voDao.Bills();
		bill = billsDao.findById(bid);
		if (bill.getUid().toString().equals(request.getSession().getAttribute("UOID").toString())) {
			billsDao.delete(bill);
			String recordPage = getParameter("r");
			if (recordPage != null && recordPage.equals("record")) {
				response.sendRedirect("Records.yy");
			} else {
				response.sendRedirect("Bills.yy");
			}
		} else {
			request.setAttribute("errpos", "修改记录");
			request.setAttribute("errmsg", "权限错误");
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/jumperror.jsp").forward(request, response);
			return;
		}
	}

	public void doAddNewbtype(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String btypename = getParameter("btypename");
		btypename = java.net.URLDecoder.decode(btypename, "UTF-8");
		PrintWriter out = response.getWriter();
		if (docheckbtypename(btypename)) {
			out.print("hasExist");
			out.close();
			return;
		}
		BillsTypeDAO billsTypeDao = new BillsTypeDAO();
		BillsType billsType = new BillsType();
		billsType.setBtypename(btypename);
		billsType.setBtypeuserid(Integer.parseInt(request.getSession().getAttribute("UOID").toString()));
		billsTypeDao.save(billsType);
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
