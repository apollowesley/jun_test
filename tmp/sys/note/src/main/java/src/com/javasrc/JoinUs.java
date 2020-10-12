/**
 * 
 * @auther wuwang
 * @createTime 2014-5-2 下午8:50:03
 */
package com.javasrc;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.globalClass.GlobalVariable;
import com.globalInterface.RequestParameter;
import com.hibernate.voDao.JoinAsAPartner;
import com.hibernate.voDao.JoinAsAPartnerDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.staticClass.Const;
import com.staticClass.MapToJson;

/**
 * 
 * 
 * @author peaches
 */
public class JoinUs extends RequestParameter {

	/**
	 * Constructor of the object.
	 */
	public JoinUs() {
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
		String a = getParameter("a");
		a = a == null ? a = "" : a;
		JoinAsAPartnerDAO joinAsAPartnerDao = new JoinAsAPartnerDAO();
		JoinAsAPartner joinAsAPartner = new JoinAsAPartner();
		if (a.equals("add")) {// 添加页面
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/joinusadd.jsp").forward(request, response);
			return;
		}
		if (a.equals("detail")) {// 查看详情页面
			String id = getParameter("id");
			joinAsAPartner = joinAsAPartnerDao.findById(Integer.parseInt(id));
			// 还原双引号
			joinAsAPartner.setJaapcontent(joinAsAPartner.getJaapcontent().replaceAll("\\\\\"", "\""));
			request.setAttribute("data", joinAsAPartner);
			// if
			// (joinAsAPartner.getUid().toString().equals(request.getSession().getAttribute("UOID").toString()))
			// {// 如果是当前用户发起的搭伙
			// request.setAttribute("state", "4");
			// List listApply = joinAsAPartnerDao.findAllApply(id);
			// request.setAttribute("listApply", listApply);
			// } else {// 如果不是
			// /* 检查当前查看用户的状态，决定显示怎样的按钮 --开始 */
			// String uid =
			// request.getSession().getAttribute("UOID").toString();
			// short state = JoinUsGetButton.getstate(uid, id);
			// request.setAttribute("state", state);
			// /* 检查当前查看用户的状态，决定显示怎样的按钮 --结束 */
			// }
			request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/joinusdetail.jsp").forward(request, response);
			return;
		}
		Integer pageNum = new Integer(getParameter("pageNum") == null ? "0" : getParameter("pageNum"));
		/* 当前页不大于0时，重新设置 */
		pageNum = pageNum >= 0 ? pageNum : 0;
		String ofTotal = joinAsAPartnerDao.findAllPartnerCount();
		/* 总页数 */
		int pages = (new Integer(ofTotal) - 1) / 20;
		/* 当前页大于总页时，重新设置 */
		pageNum = pageNum >= pages ? pages : pageNum;
		/* 显示分页数据 */
		List list = joinAsAPartnerDao.findAllPartner(pageNum, 20);
		/* 当前页 */
		request.setAttribute("pagenow", pageNum);
		request.setAttribute("pages", pages);
		request.setAttribute("ofTotal", ofTotal);
		request.setAttribute("list", list);

		request.getRequestDispatcher(GlobalVariable.getWebsite_template() + "/jsp/joinus.jsp").forward(request, response);
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

		JoinAsAPartnerDAO joinAsAPartnerDao = new JoinAsAPartnerDAO();
		JoinAsAPartner joinAsAPartner = new JoinAsAPartner();
		String UOID = request.getSession().getAttribute("UOID").toString();
		UsersDAO usersDao = new UsersDAO();

		Users users = usersDao.findById(Integer.parseInt(UOID));

		if (Const.StringIsNull(users.getTelephone())) {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("realSucess", "false");
			json.put("msg", "请先去个人中心设置手机号码，方便想要一起去的人联系你~");
			out.print(json.toString());
			out.close();
			return;
		}

		String a = getParameter("a");// 添加新的搭伙，还是加入已有的
		a = a == null ? a = "" : a;
		if (a.equals("add")) {// 新加的

			String title = getParameter("title");
			String content = getParameter("content");
			String cost = getParameter("cost");
			String thenumofperson = getParameter("thenumofperson");
			String jaappermission = getParameter("jaappermission");
			String jaapdeadline = getParameter("jaapdeadline");

			joinAsAPartner.setJaaptitle(title);
			joinAsAPartner.setJaapcontent(MapToJson.parseHtml(content));
			joinAsAPartner.setJaapcost(Double.parseDouble(cost));
			joinAsAPartner.setJaapthenumberofperson(Integer.parseInt(thenumofperson));
			joinAsAPartner.setJaappermission(Short.parseShort(jaappermission));
			joinAsAPartner.setJaappublicdate(new java.util.Date());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				joinAsAPartner.setJaapdeadline(df.parse(jaapdeadline));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			joinAsAPartner.setJaapissponsor(true);
			joinAsAPartner.setJaapadvice("");
			joinAsAPartner.setJaapsummary("");

			joinAsAPartner.setUid(users.getUid());
			joinAsAPartner.setUsername(users.getUsername());

			joinAsAPartnerDao.save(joinAsAPartner);

			PrintWriter out = response.getWriter();
			out.print(SUCCESS);
			out.close();
			return;
		} else if (a.equals("applyjoin")) {// 申请加入的
			String id = getParameter("id");
			String content = getParameter("content");
			joinAsAPartner = joinAsAPartnerDao.findById(Integer.parseInt(id));
			JoinAsAPartner joinAsAPartnerapply = new JoinAsAPartner();
			joinAsAPartnerapply.setJaapcontent("");
			joinAsAPartnerapply.setJaapadvice(content);
			joinAsAPartnerapply.setJaapsummary("");
			joinAsAPartnerapply.setJaapapplyid(Integer.parseInt(id));
			joinAsAPartnerapply.setJaappublicdate(new java.util.Date());
			joinAsAPartnerapply.setJaapdeadline(joinAsAPartner.getJaapdeadline());

			joinAsAPartnerapply.setUid(users.getUid());
			joinAsAPartnerapply.setUsername(users.getUsername());

			joinAsAPartnerapply.setJaapjoinstate((short) 1);
			joinAsAPartnerDao.save(joinAsAPartnerapply);

			PrintWriter out = response.getWriter();
			out.print(SUCCESS);
			out.close();

		} else if (a.equals("delete")) {

			String id = getParameter("id");
			joinAsAPartner = joinAsAPartnerDao.findById(Integer.parseInt(id));
			joinAsAPartnerDao.delete(joinAsAPartner);

			PrintWriter out = response.getWriter();
			out.print(SUCCESS);
			out.close();
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
