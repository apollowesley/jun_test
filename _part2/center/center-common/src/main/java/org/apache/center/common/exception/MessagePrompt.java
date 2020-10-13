package org.apache.center.common.exception;

public enum MessagePrompt {

	//用户模块异常，1000打头，0001开始，一共8位数，10000001
	USER_NAME_REGISTERED("10000001", "该用户名已注册,请更换一个");
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private MessagePrompt(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private String code;
	
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
