package org.nature4j.framework.util;

import java.util.Map;
import java.util.regex.Pattern;

import org.nature4j.framework.core.NatureMap;

public abstract class ValidatorUtil {
	
	/**
	 * 邮箱格式
	 */
	public static String emailRegx="^([a-z0-9_\\.\\-]+)@([\\da-z\\.\\-]+)\\.([a-z\\.]{2,6})$";
	
	/**
	 * 帐号格式，3-16位数字或字母（包括@ - _ .）
	 */
	public static String accountRegx= "^[a-zA-Z0-9_\\-\\.@]{3,16}$";
	
	/**
	 * 非空验证
	 */
	public static String notBankRegx = "^.+$";
	
	/**
	 * 身份证验证
	 */
	public static String idCardRegx = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
	
	/**
	 * 必须为数字验证
	 */
	public static String isNumRegex = "^\\d+$";
	/**
	 * 必须为数字，包括小数
	 */
	public static String isDecimalRegex = "^\\d+(\\.\\d+)?$";
	
	/**
	 * 手机号码，支持+86和特殊号码
	 */
	public static String mobilephoneRegex = "^(\\+86)?1\\d{2}\\-?\\d{4}\\-?\\d{4}$";
	
	/**
	 * 电话号码，格式较为复杂，只是验证了数字、括号，连接线和3到16长度
	 */
	public static String telephoneRegex = "^[\\(\\)\\-\\d]{3,16}$";
	
	/**
	 * 手机和电话号码
	 */
	public static String phoneRegex = telephoneRegex+"|"+mobilephoneRegex;
	
	
	/**
	 * 验证器
	 * @param params 请求参数集合
	 * @param errorMap 错误集合
	 * @param name 属性名
	 * @param regex 正则表达式
	 * @param msgName 错误信息名称
	 * 
	 */
	
	public static void validate(NatureMap params,Map<String, String> errorMap,String name, String regex,String msgName, String msg) {
		if (params.containsKey(name)&&!Pattern.matches(regex, params.getString(name))) {
			errorMap.put(msgName, msg);
		}
	}
	
}
