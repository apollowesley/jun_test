package com.kld.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * Session 工具类
 * @author shan
 *
 */
public class SessionUtils {
	
	public static final  String USER_MODEL ="userModel";
	
	public static HttpSession getSession(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession();
	}
	/**
	 * 这里的<SysUserDto> 其实是泛型
	 * @param o
	 */
	public static <SysUserDto> void setUser(SysUserDto o){
		getSession().setAttribute(USER_MODEL,o);
	}
	/**
	 * 获取当前登录的用户信息
	 * @return
	 */
	public static Object getCurrentUser(){
		return getSession().getAttribute(USER_MODEL);
	}
	

}
