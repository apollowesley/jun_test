package org.nature.framework.cache;

import javax.servlet.http.HttpSession;

public class HttpNativeSession implements NatureSession{
	HttpSession session;
	
	public HttpNativeSession(HttpSession session) {
		this.session = session;
	}

	public void init() {
	}

	public void set(String key, Object value) {
		session.setAttribute(key, value);
	}

	public Object get(String key) {
		return session.getAttribute(key);
	}

	public void replace(String key, Object value) {
		session.setAttribute(key, value);
	}

	public void remove(String key) {
		session.removeAttribute(key);
	}
	
	public String getLock(){
		return session.getId().intern();
	}

	public String getId() {
		return session.getId();
	}
}
