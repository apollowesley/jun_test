/**
 * 
 * @auther wuwang
 * @createTime 2014-8-11 下午9:12:24
 */
package com.globalInterface;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hibernate.voDao.GeneralDao;

/**
 * 
 * 
 * @author peaches
 */
public class RequestParameter extends HttpServlet {
	private HttpServletRequest request = null;
	public String SUCCESS = "{\"realSucess\":\"true\",\"success\":\"true\"}";
	public JSONObject FAIL = JSONObject.fromObject("{\"realSucess\":\"false\",\"success\":\"true\"}");
	protected static GeneralDao generalDao = new GeneralDao();

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		super.service(request, response);
	}

	/**
	 * 获取参数用的
	 * 
	 * @param s
	 * @return
	 * @createTime 2014-7-16 下午9:29:04
	 */
	public String getParameter(String s) {
		return PreparedParameter.setString(request.getParameter(s));
	}

	/**
	 * 判定一个字符串是否为空，包含 null|""|empty
	 * 
	 * @param s
	 * @return
	 * @createTime 2014-8-22 下午8:23:54
	 */
	public boolean StringIsEmpty(String s) {
		return s == null || "".equals(s) || s.isEmpty();
	}
}
