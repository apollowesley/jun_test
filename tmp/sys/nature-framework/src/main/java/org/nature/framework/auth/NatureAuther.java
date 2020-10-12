package org.nature.framework.auth;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nature.framework.cache.NatureContext;
import org.nature.framework.core.NatureMap;
import org.nature.framework.util.CollectionUtil;
import org.nature.framework.util.StringUtil;

public class NatureAuther {
	

	/**
	 * login
	 * 
	 * @param username
	 * @param password
	 * @desc key of userinfo in session is 'current_user';key of prems is 'current_prems'
	 * @return 0 login success,1 username is not exist，2 password is error，3 no prems
	 */
	public static int login(Object username, Object password,boolean rememberMe) {
		int authUsernamePassword = authUsernamePassword(username, password, rememberMe);
		if (authUsernamePassword==0) {
			NatureMap userInfo = AuthService.getUserInfo(username);
			HttpSession session = NatureContext.getRequest().getSession();
			session.setAttribute(AuthConstant.CURRENT_USER, userInfo);
			Set<String> prems = AuthService.getPrems(username);
			if (CollectionUtil.isNotEmpty(prems)) {
				session.setAttribute(AuthConstant.CURRENT_PREMS, prems);
			} else {
				return 3;
			}
		}
		
		return authUsernamePassword;
	}



	private static int authUsernamePassword(Object username, Object password, boolean rememberMe) {
		String storePassword = AuthService.getStorePassword(username);
		if (StringUtil.isEmpty(storePassword)) {
			return 1;
		}
		if (!storePassword.equals(password)) {
			return 2;
		} 
		if (rememberMe) {
			AuthCookie.saveAuth(username, password);
		}
		return 0;
	}

	
	
	/**
	 * auth prem
	 * 
	 * @param url
	 * @return 0 ok,1 not login,2 no prem
	 */
	public static int hasPrem(String url){
		Set<String> systemPrems = AuthService.getAllPrems();
		NatureMap currentUser = getCurrentUser();
		if (systemPrems.contains(url)) {
			if (currentUser!=null&&!currentUser.isEmpty()) {
				if (getCurrentPrems().contains(url)) {
					return 0;
				}else {
					return 2;
				}
			}else{
				return 1;
			}
		}else{
			return 0;
		}
	}
	
	public static NatureMap getCurrentUser() {
		Object current_user = NatureContext.getRequest().getSession().getAttribute(AuthConstant.CURRENT_USER);
		NatureMap natureMap = null ;
		if (current_user!=null) {
			natureMap = (NatureMap) current_user;
		}else{
			String[] auths = AuthCookie.getAuth();
			if (StringUtil.isNotEmpty(auths[0])) {
				int authUsernamePassword = authUsernamePassword(auths[0], auths[1], true);
				if (authUsernamePassword==0) {
					natureMap = AuthService.getUserInfo(auths[0]);
					NatureContext.getRequest().getSession().setAttribute(AuthConstant.CURRENT_USER, natureMap);
					Set<String> prems = AuthService.getPrems(auths[0]);
					if (CollectionUtil.isNotEmpty(prems)) {
						NatureContext.getRequest().getSession().setAttribute(AuthConstant.CURRENT_PREMS, prems);
					}
				}
			}
		}
		return natureMap;
	}
	
	@SuppressWarnings("unchecked")
	public static Set<String>  getCurrentPrems() {
		return (Set<String>) NatureContext.getRequest().getSession().getAttribute(AuthConstant.CURRENT_PREMS);
	}
	
	public static void logout(){
		HttpSession session = NatureContext.getRequest().getSession();
		session.removeAttribute(AuthConstant.CURRENT_USER);
		session.removeAttribute(AuthConstant.CURRENT_PREMS);
		AuthCookie.clearAuth();
	}
	
	

}
