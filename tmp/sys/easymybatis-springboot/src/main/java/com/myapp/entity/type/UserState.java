package com.myapp.entity.type;

import net.oschina.durcframework.easymybatis.handler.BaseEnum;

public enum UserState implements BaseEnum<Byte> {
	Reg((byte)0),Valid((byte)1),Forbiden((byte)2)
	;

	private byte state;
	
	UserState(byte state) {
		this.state = state;
	}
	
	@Override
	public Byte getCode() {
		return state;
	}

}
