/**
 * 
 * @auther wuwang
 * @createTime 2014-12-27 下午8:29:00
 */
package com.api.sso;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.UserLoginLog;
import com.hibernate.voDao.UserLoginLogDAO;
import com.listener.MySessionContext;

/**
 * 
 * 
 * @author peaches
 */
public class Login extends RequestParameter2 {
	private static final Logger log = LoggerFactory.getLogger(Login.class);
	private UserLoginLogDAO userLoginLogDao;

	public Login(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void initClass() {
		userLoginLogDao = new UserLoginLogDAO();
	}

	/**
	 * 用户session验证。默认false会返回json要求重新登录，程序中只需要处理true，false直接返回就可以
	 * 
	 * @param sessionid
	 * @return boolean
	 * @createTime 2014-5-25 下午9:03:58
	 */
	public boolean sessionValide() throws ServletException, IOException {
		/** false 未登录 **/
		boolean hadLogin = false;
		/** 从cookies获取sessionId **/
		if (getSession().isNew()) {

			/** 如果没有cookie，则从监听器获取 **/
			System.out.println("UOID:" + getParameter("UOID"));
			System.out.println("UName:" + getParameter("UName"));
			String sessionid = getParameter("sessionid");

			MySessionContext myc = MySessionContext.getInstance();
			HttpSession sess = myc.getSession(sessionid);
			if (sess == null || sess.isNew()) {

			} else {
				log.info("用户id" + sess.getAttribute("UOID") + "已经登录");
				return true;
			}
		} else {
			Object UOID = request.getSession().getAttribute("UOID");
			if (UOID != null) {
				return true;
			}
		}

		if (!hadLogin) {
			JSONObject jsono = new JSONObject();
			jsono.put("code", "209");// 要求重新登录
			jsono.put("status", "error");
			jsono.put("option", "signinagain");
			returnJson(jsono.toJSONString());
		}
		return hadLogin;
	}

	/**
	 * 记录登录日志
	 * 
	 * @param uid
	 * @param ip
	 * @param remark
	 * @createTime 2015-1-9 下午9:32:25
	 */
	public static void LogLogin(String uid, String ip, String remark) {
		UserLoginLog userLoginLog = new UserLoginLog();
		userLoginLog.setFkUid(Integer.valueOf(uid));
		userLoginLog.setLoginTime(new Date());
		userLoginLog.setLoginIp(ip);
		userLoginLog.setRemark(remark);
		new UserLoginLogDAO().save(userLoginLog);
	}
}
