package com.ly.utils.string;

import java.util.regex.Pattern;

/**
 * String格式判断类
 * 
 * @version 1.0
 */
public class StringJudge {
	
	/**
	 * 空
	 */
	public static boolean isEmpty(String str){
		if(str==null || str.trim().equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 数字
	 */
	public static boolean isNumber(String str){
		return Pattern.matches("^[0-9]+$", str);
	}
	
	/**
	 * 字母
	 */
	public static boolean isLetter(String str){
		return Pattern.matches("^[a-zA-Z]+$", str);
	}
	/**
	 * 汉字
	 */
	public static boolean isHanzi(String str){
		return Pattern.matches("^[\u4e00-\u9fa5]+$", str);
	}
	
	/**
	 * 字母和数字
	 */
	public static boolean isLetterAndNumber(String str){
		return Pattern.matches("^[A-Za-z0-9]+$", str);
	}
	
	/**
	 * 数字、字母、下划组成
	 */
	public static boolean isName(String str){
		return Pattern.matches("^[A-Za-z0-9_]+$", str);
	}
	
	/**
	 * 邮箱地址
	 */
	public static boolean isEmail(String str){
		return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", str);
	}
	
	/**
	 * 域名 (http://)
	 */
	public static boolean isHttp(String str){
		return Pattern.matches("[a-zA-z]+://[^\\s]*", str);
	}
	
	/**
	 * 手机号码
	 */
	public static boolean isPhone(String str){
		return Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", str);
	}
	
	/**
	 * 帐号(字母开头，允许字母数字)
	 */
	public static boolean isUsername(String str,int start,int end){
		return Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{"+start+","+end+"}$", str);
	}
	
	/**
	 * 密码(以字母开头，只能包含字母、数字和下划线)
	 */
	public static boolean isPassword(String str,int start,int end){
		return Pattern.matches("^[a-zA-Z]\\w{"+start+","+end+"}$", str);
	}
	/**
	 * 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符)
	 */
	public static boolean isGigPassword(String str,int start,int end){
		return Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{"+start+","+end+"}$", str);
	}
	
	/**
	 * IP
	 */
	public static boolean isIP(String str){
		return Pattern.matches("((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))", str);
	}
}
