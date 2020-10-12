/**
 * 
 * @auther wuwang
 * @createTime 2014-8-11 下午9:12:24
 */
package com.globalInterface;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibernate.voDao.GeneralDao;
import com.listener.MySessionContext;

/**
 * 
 * 
 * @author peaches
 */
public abstract class RequestParameter2 extends HttpServlet {

	private static final Logger log = LoggerFactory.getLogger(RequestParameter2.class);

	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	public String SUCCESS = "{\"realSucess\":\"true\",\"success\":\"true\"}";
	public JSONObject FAIL = JSONObject.fromObject("{\"realSucess\":\"false\",\"success\":\"true\"}");
	protected static GeneralDao generalDao = new GeneralDao();

	private HttpSession userSession;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		this.response = response;
		initClass();
		// super.service(request, response);
		/**
		 * super.super.super这样一直调用到HttpServlet.service()如果没有覆盖doGet()和doPost()
		 * 就会导致405错误
		 */
	}

	public String getHost() {
		String host = request.getServerName();
		int port = request.getServerPort();
		return host + (port == 80 ? "" : ":" + port);
	}

	public String getDir() {
		String path = this.getClass().getClassLoader().getResource("").getPath().replace("/WEB-INF/classes/", "");
		System.out.println(path);
		return path;
	}

	/**
	 * 获取session
	 * 
	 * @param s
	 * @return
	 * @createTime 2014-12-18 下午10:42:06
	 */
	public HttpSession getSession() {
		return request.getSession();
	}

	/**
	 * 返回session，在session监听器中
	 * 
	 * @return
	 * @createTime 2015-5-9 下午10:50:51
	 */
	private HttpSession getSessionFromListener() {

		String sessionid = getParameter("sessionid");
		String sessionidCookie = getSession().getId();

		MySessionContext myc = MySessionContext.getInstance();
		HttpSession sess = myc.getSession(sessionid);
		HttpSession sessCookie = myc.getSession(sessionidCookie);
		if (sess != null) {
			log.info("---session is old param");
			return sess;
		} else if (sessCookie != null) {
			log.info("---session is old cookie");
			return sessCookie;
		}
		return null;
	}

	/**
	 * 获取用户session
	 * 
	 * @return
	 * @createTime 2015-5-9 下午10:57:03
	 */
	public HttpSession getUserSession() {
		if (userSession == null) {
			userSession = getSessionFromListener();
		}
		return userSession;
	}

	/**
	 * 设置session
	 * 
	 * @param userSession
	 * @createTime 2015-5-9 下午10:57:15
	 */
	public void setUserSession(HttpSession userSession) {
		this.userSession = userSession;
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

	/**
	 * request 返回json字符串
	 * 
	 * @param s
	 * @throws IOException
	 * @createTime 2014-11-29 上午1:11:01
	 */
	public void returnJson(String s) throws IOException {
		PrintWriter p = response.getWriter();
		p.write(s);
	}

	/**
	 * 初始化类，由于servlet不是线程安全，所以公用变量在这个类中创建实例
	 * 
	 * @createTime 2014-11-29 上午2:16:24
	 */
	public abstract void initClass();
}
