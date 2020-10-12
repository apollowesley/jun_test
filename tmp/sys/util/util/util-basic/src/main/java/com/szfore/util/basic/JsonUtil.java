package com.szfore.util.basic;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json工具类
 * @author 罗季晖
 *
 */
public class JsonUtil {
	/**
	 * bean转json
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		if(obj == null){
			return null;
		}
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(obj);
	}

	@SuppressWarnings("rawtypes")
	public static Map toMap(String jsonStr) 
	{
		Gson gson = new Gson();
		Map map = gson.fromJson(jsonStr, HashMap.class);
		
		return map;
	}

	
}
