/**
 * 
 * @auther wuwang
 * @createTime 2014-11-29 下午10:38:56
 */
package com.staticClass;

import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * 
 * @author peaches
 */
public class JsonUtils {
	public static JSONArray parseArrayFromObject(Object obj) {
		if (obj == null) {
			obj = "[]";
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor());
		return JSONArray.fromObject(obj, jsonConfig);
	}

	public static JSONArray parseArrayFromObject(Object obj, int dateCode) {
		if (obj == null) {
			obj = "[]";
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateCode));
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor(dateCode));
		return JSONArray.fromObject(obj, jsonConfig);
	}

	public static JSONObject parseObjectFromObject(Object obj) {
		if (obj == null) {
			obj = "{}";
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor());
		return JSONObject.fromObject(obj, jsonConfig);
	}

	public static JSONObject parseObjectFromObject(Object obj, int dateCode) {
		if (obj == null) {
			obj = "{}";
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateCode));
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor(dateCode));
		return JSONObject.fromObject(obj, jsonConfig);
	}
}
