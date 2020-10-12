package com.cnblogs.honoka.utils;

import static java.lang.System.*;

/**
 * 常用功能
 * @author honoka
 */
public class ToolsUtil {
	/**
	 * 判断对象是否非空
	 * @author honoka
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {

		return (null != obj)&&(!"".equals(obj.toString()));
	}
}