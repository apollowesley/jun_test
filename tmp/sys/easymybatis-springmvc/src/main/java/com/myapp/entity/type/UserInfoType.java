package com.myapp.entity.type;

import net.oschina.durcframework.easymybatis.handler.BaseEnum;

public enum UserInfoType implements BaseEnum<String> {
	INVALID("0"),VALID("1")
	;

	private String status;

	UserInfoType(String type) {
		this.status = type;
	}
	
	@Override
	public String getCode() {
		return status;
	}
}
