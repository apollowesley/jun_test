package org.durcframework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author hc.tang 2012-10-24
 */
public class JsonUtil {	

	/**
	 * 把对象转换成json格式的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 把对象转换成json格式的字符串
	 * @param obj
	 * @param dateFormat 日期格式,如"yyyy-MM-dd".如果有日期对象,则都会转成这种格式
	 * @return
	 */
	public static String toJsonString(Object obj,String dateFormat) {
		return JSON.toJSONStringWithDateFormat(obj, dateFormat);
	}

	/**
	 * 把对象转换成json对象
	 * 
	 * @param obj
	 * @return
	 */
	public static JSONObject toJsonObj(Object obj,String dateFormat) {
		return JSON.parseObject(toJsonString(obj,dateFormat));
	}
}
