package com.jake.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jake.bean.Message;
import com.jake.dao.DBC;
import com.jake.util.StringUtil;

public class AddMessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		DBC dbc = new DBC();
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String sex = request.getParameter("sex");
		String content = request.getParameter("content");
		String heading = request.getParameter("heading");
		String mood = request.getParameter("mood");
		String email = request.getParameter("email");
		String qq = request.getParameter("qq");
		if (StringUtil.isEmpty(name) && StringUtil.isEmpty(title)
				&& StringUtil.isEmpty(sex) && StringUtil.isEmpty(content)
				&& StringUtil.isEmpty(heading) && StringUtil.isEmpty(mood)
				&& StringUtil.isEmpty(email) && StringUtil.isEmpty(qq)) {
			Message mess = new Message();
			mess.setName(name);
			mess.setTitle(title);
			mess.setSex(Integer.parseInt(sex));
			mess.setHeading(Integer.parseInt(heading));
			mess.setQq(qq);
			mess.setEmail(email);
			mess.setMood(Integer.parseInt(mood));
			mess.setContent(content);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String pubtime = sdf.format(date);
			mess.setPubtime(pubtime);
			try {
				dbc.addMess(mess);
				response.sendRedirect("/guestbook01/addSuccess.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			out.println("<script>alert('请填写完整再提交，谢谢');history.back()</script>");
		}

		out.flush();
		out.close();
	}

}
