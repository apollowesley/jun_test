package org.nature.framework.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.cache.NatureContext;
import org.nature.framework.util.CastUtil;

public class AuthCookie {
	public static void saveAuth(Object username, Object password) {
		HttpServletResponse response = NatureContext.getResponse();
		Cookie usernameCookie = new Cookie(AuthConstant.USERNAME,CastUtil.castString(username) );
		usernameCookie.setPath("/");
		usernameCookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(usernameCookie);
		Cookie passwordCookie = new Cookie(AuthConstant.PASSWORD,CastUtil.castString(password) );
		passwordCookie.setMaxAge(Integer.MAX_VALUE);
		passwordCookie.setPath("/");
		response.addCookie(passwordCookie);
	}

	public static String[] getAuth() {
		Cookie[] cookies = NatureContext.getRequest().getCookies();
		String[] auths = new String[2];
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (AuthConstant.USERNAME.equals(cookie.getName())) {
					auths[0] = cookie.getValue();
				} else if (AuthConstant.PASSWORD.equals(cookie.getName())) {
					auths[1] = cookie.getValue();
				}
			}
		}
		return auths;
	}

	public static void clearAuth() {
		HttpServletResponse response = NatureContext.getResponse();
		Cookie usernameCookie = new Cookie(AuthConstant.USERNAME, null);
		usernameCookie.setPath("/");
		usernameCookie.setMaxAge(0);
		response.addCookie(usernameCookie);
		Cookie passwordCookie = new Cookie(AuthConstant.PASSWORD, null);
		passwordCookie.setPath("/");
		passwordCookie.setMaxAge(0);
		response.addCookie(passwordCookie);
	}
}
