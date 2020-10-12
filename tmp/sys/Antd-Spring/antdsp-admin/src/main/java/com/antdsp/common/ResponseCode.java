package com.antdsp.common;

public enum ResponseCode {

	OK(1200 , "正常"),
	ERROR(1201,"未正常处理"),
	REJECT(1207 , "拒绝"),
	UNAUTHORIZED(1401 , "未登录"),
	FORBIDDEN(1403 , "权限不足");
	
	
	private final int value;
	private final String msg;
	
	ResponseCode(int value , String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMsg() {
		return msg;
	}
	
}
