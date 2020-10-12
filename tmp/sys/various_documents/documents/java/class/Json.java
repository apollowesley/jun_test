package com.qxzl.util;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
public final class Json implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8061204212730889533L;

	/**
	 * 是否成功
	 * 默认true
	 */
	private boolean success = true;

	/**
	 * 数据对象
	 * 如果是列表查询放入pager 对象
	 */
	private Object obj;

	/**
	 * 错误编码
	 *
	 */
	private String code;

	/**
	 * 错误提示信息
	 */
	private String message;

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public Object getObj() {
		return this.obj;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer("{")  ;
		str.append("\"code\":").append("\"").append(this.code).append("\",");
		str.append("\"success\":").append("\"").append(this.success).append("\",");
		str.append("\"message\":").append("\"").append(this.message).append("\",");
		str.append("\"obj\":").append(JSONObject.toJSONString(this.obj)).append("}");
//		System.out.println("================================================================================================================================================================================================================================================================================================================================================================");
//		System.out.println("\n"+str.toString());
//		System.out.println("\n================================================================================================================================================================================================================================================================================================================================================================");
		JsonLog.println(str.toString());
		return str.toString();
	}

}