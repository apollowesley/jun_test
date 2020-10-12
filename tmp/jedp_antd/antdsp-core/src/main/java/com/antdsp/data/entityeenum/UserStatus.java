package com.antdsp.data.entityeenum;

public enum UserStatus {
	NORMAL("正常"),
	DISABLED("已禁用"),
	FORBIDDEN("异常");
	
	private String value;
	
	UserStatus(String value){
		this.value = value;
	}
}
