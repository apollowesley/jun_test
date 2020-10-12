package com.evil.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

/**
 * Struts2������
 * @author evil
 */
public class Struts2Utils {


	// -- ȡ��Request/Response/Session�ļ򻯺��� --//
	/**
	 * ȡ��HttpSession�ļ򻯺���.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * ȡ��HttpSession�ļ򻯺���.
	 */
	public static HttpSession getSession(boolean isNew) {
		return ServletActionContext.getRequest().getSession(isNew);
	}

	/**
	 * ȡ��HttpSession��Attribute�ļ򻯺���.
	 */
	public static Object getSessionAttribute(String name) {
		HttpSession session = getSession();
	
		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * ����HttpRequest��Attribute�ļ򻯺���.
	 */
	public static void setRequestAttribute(String key, String value) {
		getRequest().setAttribute(key, value);
	}

	/**
	 * ȡ��HttpRequest�ļ򻯺���.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * ȡ��HttpRequest��Parameter�ļ򻯷���.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * ȡ��HttpResponse�ļ򻯺���.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

}
