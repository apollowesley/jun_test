package com.javasrc;

import java.io.IOException;
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
import com.hibernate.voDao.BillsType;
import com.hibernate.voDao.BillsTypeDAO;
import com.staticClass.Const;
import com.staticClass.MapToJson;
import com.staticClass.TopicKeyWords;

public class Records extends RequestParameter {

	/**
	 * Constructor of the object.
	 */
	public Records() {
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
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);
		String today = currentdatems1.substring(0, 5) + currentdatems1.substring(5, 7) + currentdatems1.substring(7);
		/* 获得参数 */
		String startDate = getParameter("startDate");

		startDate = startDate != null ? startDate : lastMonthDay(startDate, currentdatems1);

		String endDate = getParameter("endDate");
		endDate = endDate != null ? endDate : today;
		Integer pageNum = new Integer(getParameter("pageNum") == null ? "0" : getParameter("pageNum"));
		/* parameter1 */
		String parameter1 = "";
		String travelleader = getParameter("travelleader");
		if (!StringIsEmpty(travelleader)) {
			parameter1 += "bbetravelleader," + "1";
		}
		String swd = getParameter("swd");// search word
		if (!StringIsEmpty(swd)) {
			parameter1 += "btype," + swd;
		}
		/* btypeword是名称，对应的其实是swd，swd是id 相同的参数 */
		String btypeword = getParameter("btypeword");
		if (!StringIsEmpty(btypeword)) {
			// 账单类型名称，先取出名称对应的id，再查询
			BillsTypeDAO bills_typeDao = new BillsTypeDAO();
			BillsType bills_type = new BillsType();
			btypeword = java.net.URLDecoder.decode(btypeword, "UTF-8");
			bills_type = (BillsType) bills_typeDao.findByUserIdandtypename(request.getSession().getAttribute("UOID"), btypeword).get(0);
			parameter1 += "btype," + bills_type.getBtypeid();
			swd = bills_type.getBtypeid().toString();
		}
		/* parameter2 */
		String swdlike = getParameter("swdlike");// search word
		String parameter2 = "";
		if (!StringIsEmpty(swdlike)) {
			swdlike = java.net.URLDecoder.decode(swdlike, "UTF-8");
			parameter2 += "bcaption," + swdlike;
		}
		String UOID = String.valueOf(request.getSession().getAttribute("UOID"));
		String parameter3 = "(uid='" + UOID + "' or uid in(select user_id_1 from relationship_friends where user_id_2='" + UOID
				+ "' and status='2') or uid in(select user_id_2 from relationship_friends where user_id_1='" + UOID + "' and status='2')) and ";

		/* 当前页不大于0时，重新设置 */
		pageNum = pageNum >= 0 ? pageNum : 0;
		/* 用来读取数据 */
		BillsDAO billsDao = new BillsDAO();
		/* 显示分页数据的条数 */
		String ofTotal = billsDao.findByPropertyPagingCounts(parameter1, parameter2, parameter3, pageNum, 20, startDate + " 00:00:00", endDate + " 23:59:59");
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
		List list = billsDao.findByPropertyPaging(parameter1, parameter2, parameter3, pageNum, 20, startDate + " 00:00:00", endDate + " 23:59:59");
		list = TopicKeyWords.addUrlLink(list);
		/* 当前页 */
		request.setAttribute("pagenow", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("ofTotal", ofTotal);
		request.setAttribute("list", list);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("lastmonthday", lastMonthDay("", currentdatems1));
		request.setAttribute("threemonthday", lastMonthDay("", lastMonthDay("", lastMonthDay("", currentdatems1))));
		/* 十二个月 */
		String lastyear = today;
		for (int i = 0; i < 12; i++) {
			lastyear = lastMonthDay("", lastyear);
		}
		request.setAttribute("lastyear", lastyear);

		request.setAttribute("today", today);
		request.setAttribute("swd", swd);
		request.setAttribute("swdlike", swdlike);
		request.setAttribute("btypeword", btypeword);
		request.setAttribute("tagid", getParameter("tagid"));
		dealMultiImage(list);
		String result = MapToJson.parseJson(list);
		request.setAttribute("listjson", result);
		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/records.jsp").forward(request, response);
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

	/** 计算上一个月的今天 */
	public String lastMonthDay(String startDate, String currentdatems1) {
		/* 先计算年份和月份 */

		startDate = Integer.parseInt(currentdatems1.substring(5, 7)) - 1 != 0 ? currentdatems1.substring(0, 5) + (Integer.parseInt(currentdatems1.substring(5, 7)) - 1) : Integer
				.parseInt(currentdatems1.substring(0, 4)) - 1 + "-12";

		/* 再计算日期,按月份不同 */
		switch (Integer.parseInt(startDate.substring(5))) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
			startDate = startDate.substring(0, 5) + "0" + startDate.substring(5);// 月份加0
		case 10:
		case 12:
			startDate += currentdatems1.substring(7);
			break;
		case 4:
		case 6:
		case 9:
			startDate = startDate.substring(0, 5) + "0" + startDate.substring(5);// 月份加0
		case 11:
			if (currentdatems1.substring(7).equals("-31")) {
				startDate += "-30";
			} else {
				startDate += currentdatems1.substring(7);
			}
			break;
		case 2:
			startDate = startDate.substring(0, 5) + "0" + startDate.substring(5);// 月份加0
			Integer year = Integer.parseInt(startDate.substring(0, 4));
			if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0)/* 闰年29天 */
			{
				if (Integer.parseInt(currentdatems1.substring(8)) > 29) {
					startDate += "-29";
				} else {
					startDate += currentdatems1.substring(7);
				}
			} else {
				if (Integer.parseInt(currentdatems1.substring(8)) > 28) {
					startDate += "-28";
				} else {
					startDate += currentdatems1.substring(7);
				}
			}
			break;
		}

		return startDate;
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
