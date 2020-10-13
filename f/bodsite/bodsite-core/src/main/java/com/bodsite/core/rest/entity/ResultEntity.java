package com.bodsite.core.rest.entity;

import java.util.Map;

/**
 * 返回响应结果体
 * 
 * @author bod
 * @param <T>
 * @date 2016年12月14日 下午6:22:10
 *
 */
public class ResultEntity {

	public static final int SUCCESS_CODE = 0;
	public static final int ERROR_CODE = 400000;

	public int code;//状态码
	private String message;//消息
	private Object data;//内容
	private Map extra;//额外内容

	public ResultEntity(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultEntity(int code, String message, Object data, Map extra) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.extra = extra;
	}

	public ResultEntity(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map getExtra() {
		return extra;
	}

	public void setExtra(Map extra) {
		this.extra = extra;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
