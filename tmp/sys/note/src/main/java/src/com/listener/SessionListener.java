package com.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听器
 * 
 * @author peaches
 */
public class SessionListener implements HttpSessionListener {
	public static Map userMap = new HashMap();
	private final MySessionContext myc = MySessionContext.getInstance();

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		/**
		 * @1. 网页浏览不需要这个
		 * @2. Android上没有session保持，每次都新建一个session，但是又不用，非常浪费，在Android登录成功后手动添加即可
		 */
		// myc.AddSession(httpSessionEvent.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		this.myc.DelSession(session);
	}
}