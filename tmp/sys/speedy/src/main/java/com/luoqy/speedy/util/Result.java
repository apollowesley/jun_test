package com.luoqy.speedy.util;

/**
 * @author qf
 * 返回数据自定义
 * message 提示信息
 * state 状态码 500 失败  200成功  状态码同步web请求状态码  若需其他自定义状态请自行定义
 * data 返回数据，若没有返回数据时候，请设置为null
 */
public class Result {
	private String message;
	private int state;
	private Object data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Result() {
		
	}
	private Result(int state, String message, Object data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public static Result success(String message, Object data) {
		return new Result(200, message, data);
	}

	public static Result error(String message) {
		return new Result(500, message, new Object[0]);
	}
}
