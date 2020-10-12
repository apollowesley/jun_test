package com.antdsp.common;

public class AntdspResponse {
	
	private int code;
	
	private boolean success;
	
	private String message;

	public AntdspResponse() {
	}
	
	public AntdspResponse(ResponseCode code , boolean success){
		this(code.getValue() , success , code.getMsg());
	}

	public AntdspResponse(ResponseCode code , boolean success , String message) {
		this(code.getValue() , success, message);
	}
	
	public AntdspResponse(int code , boolean success , String message) {
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static AntdspResponse success() {
		return success("操作成功");
	}
	
	public static AntdspResponse success(String msg) {
		return success(ResponseCode.OK , msg);
	}
	
	public static AntdspResponse success(ResponseCode code , String msg) {
		
		return new AntdspResponse(code, true , msg);
	}
	
	public static AntdspResponse error() {
		return error("操作失败");
	}

	public static AntdspResponse error(String msg) {
		return error(ResponseCode.ERROR , msg);
	}
	
	public static AntdspResponse error(ResponseCode code , String msg) {
		return new AntdspResponse(code, false, msg);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
