package org.durcframework.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.durcframework.core.util.RequestUtil;

public enum UserContext {
	INSTANCE;

	private static String S_KEY_USER = "S_KEY_USER";
	private static PathMapper<Boolean> pathMapper = null;

	public static UserContext getInstance() {
		return INSTANCE;
	}

	/**
	 * 获取用户
	 * @return
	 */
	public <T extends IUser> T getUser() {
		HttpSession session = WebContext.getInstance().getSession();
		if(session != null){
			return this.getUser(session);
		}
		return null;
	}
	
	/**
	 * 获取用户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IUser> T getUser(HttpSession session) {
		return (T) session.getAttribute(S_KEY_USER);
	}

	/**
	 * 保存用户
	 * @param BackUser
	 */
	public <T extends IUser> void setUser(T t) {
		HttpSession session = WebContext.getInstance().getSession();
		if(session != null){
			session.setAttribute(S_KEY_USER, t);
		}
	}

	public boolean isAdmin() {
		return "admin".equals(getUser().getUsername());
	}
	
	/**
	 * 是否为无需登录就能访问的url
	 * @param request
	 * @return
	 */
	public static boolean isExcludeUrl(HttpServletRequest request) {
		String uri = RequestUtil.getRequestPath(request);
		return getPathMapper().get(uri) != null;
	}

	public static PathMapper<Boolean> getPathMapper() {
		return pathMapper;
	}

	public static void setPathMapper(PathMapper<Boolean> pathMapper) {
		UserContext.pathMapper = pathMapper;
	}
	
}
