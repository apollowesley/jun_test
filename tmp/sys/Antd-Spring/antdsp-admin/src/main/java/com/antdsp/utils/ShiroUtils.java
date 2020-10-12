package com.antdsp.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.antdsp.common.exception.CaptchaException;
import com.antdsp.data.entity.User;

public class ShiroUtils {
	
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}
	
	public static Object getSeesionAttribute(Object key) {
		return getSession().getAttribute(key);
	}
	
	public static User currentUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}
	
	public static String getCaptcha() {
		String key = Constants.CAPTCHA_SESSION_KEY;
		Object captcha = getSeesionAttribute(key);
		if(captcha == null) {
			throw new CaptchaException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return captcha.toString();
	}
}
