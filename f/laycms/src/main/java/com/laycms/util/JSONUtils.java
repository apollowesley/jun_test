/**
 * 
 */
package com.laycms.util;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * 生成一些自定义的JSON和一些JSON常量，用户给DWZ结果返回
 * @author <p>Innate Solitary 于 2012-5-19 下午12:18:24</p>
 *
 */
public final class JSONUtils {
	
	public static final JSONObject SAVE_SUCCESS = new JSONObject();
	public static final JSONObject DELETE_SUCCESS = new JSONObject();
	
	static {
		SAVE_SUCCESS.put("statusCode", "200");
		SAVE_SUCCESS.put("message", "保存成功");
		SAVE_SUCCESS.put("navTabId", "main");
		SAVE_SUCCESS.put("callbackType", "closeCurrent");
		SAVE_SUCCESS.put("forwardUrl", "");
		
		DELETE_SUCCESS.put("statusCode", "200");
		DELETE_SUCCESS.put("message", "删除成功");
		DELETE_SUCCESS.put("navTabId", "main");
	}
	
	/**
	 * 添加，修改，删除后需要给页面返回一个json，异步树通过此json，进行操作
	 * @return
	 */
	public static JSONObject getJsonResult(String treeId, String treeNodeId, String statusCode, String message, String callbackType, String navTabId) {
		return getJsonResult(treeId, treeNodeId, statusCode, message, callbackType, navTabId, null);
	}
	
	public static JSONObject getJsonResult(String treeId, String treeNodeId, String statusCode, String message, String callbackType, String navTabId, String forwardUrl) {
		JSONObject json = new JSONObject();
		json.put("treeId", treeId);
		json.put("treeNodeId", treeNodeId);
		json.put("statusCode", StringUtils.isEmpty(statusCode) ? "200" : statusCode);
		json.put("message", message);
		if(StringUtils.isNotEmpty(callbackType)) {
			json.put("callbackType", callbackType);
		}
		if(StringUtils.isNotEmpty(navTabId)) {
			json.put("navTabId", navTabId);
		}
		if(StringUtils.isNotEmpty(forwardUrl)) {
			json.put("forwardUrl", forwardUrl);
		}
		return json;
	}
	
	public static JSONObject getJsonResult(String statusCode, String message) {
		return getJsonResult(null, null, statusCode, message, null, null);
	}
	
	public static JSONObject getJsonResult(String statusCode, String message, String callbackType, String navTabId) {
		return getJsonResult(null, null, statusCode, message, callbackType, navTabId, null);
	}
	public static JSONObject getSuccessJsonResult(String callbackType,String navTabId) {
		return getJsonResult(null, null, "200", "保存成功", callbackType, navTabId, null);
	}
	public static JSONObject getSuccessJsonResult(String navTabId) {
		return getJsonResult(null, null, "200", "保存成功", "closeCurrent", navTabId, null);
	}
	
	public static JSONObject getFailJsonResult(String message,String navTabId) {
		return getJsonResult(null, null, "300", message, "closeCurrent", navTabId, null);
	}
	public static JSONObject getFailJsonResult(String message) {
		return getJsonResult(null, null, "300", message, "closeCurrent", "main", null);
	}
	
}
