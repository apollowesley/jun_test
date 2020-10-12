package com.tbc.soa.serialize.json;

import java.util.Arrays;

public class ObjectWithClassProperty {
	String name;
	Class<?> type;
	private Class<?>[] typeArray;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ObjectWithClassProperty [name=" + name + ", type=" + type
				+ ", typeArray=" + Arrays.toString(getTypeArray()) + "]";
	}
	public void setTypeArray(Class<?>[] typeArray) {
		this.typeArray = typeArray;
	}
	public Class<?>[] getTypeArray() {
		return typeArray;
	}
	
	
}
