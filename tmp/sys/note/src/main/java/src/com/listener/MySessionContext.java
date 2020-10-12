package com.listener;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例 加锁
 * 
 * @author peaches
 */
public class MySessionContext {
	private static final Logger log = LoggerFactory.getLogger(MySessionContext.class);

	private static MySessionContext instance = new MySessionContext();;
	private final HashMap mymap;
	private final HashMap<String, String> mymapName;

	private MySessionContext() {
		this.mymap = new HashMap();
		this.mymapName = new HashMap<String, String>();
	}

	public static MySessionContext getInstance() {
		return instance;
	}

	public synchronized void AddSession(HttpSession session) {
		String key = String.valueOf(session.getAttribute("UOID"));
		log.info("*********************当前在线人数总计：" + String.valueOf(this.mymap.size()) + "|" + String.valueOf(this.mymapName.size()) + "*********************");
		/**
		 * 如果这个用户已经存在mymapName中，则获取出sessionid并从mymap中删除。
		 */
		if (this.mymapName.containsKey(key)) {
			this.mymap.remove(this.mymapName.get(key));
			this.mymapName.remove(key);
			log.info("此用户已经在其它移动设备上登录");
		}
		/**
		 * 把session加入到mymap中。
		 */
		if (session != null) {
			/**
			 * 保存sessionID,session
			 */
			this.mymap.put(session.getId(), session);
			/**
			 * 保存UOID,sessionID。用于验证一个用户是否已经登录，如果是，则移除原有的session。
			 */
			this.mymapName.put(key, session.getId());
		}
	}

	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			this.mymap.remove(session.getId());
		}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null) {
			return null;
		}
		return (HttpSession) this.mymap.get(session_id);
	}

}