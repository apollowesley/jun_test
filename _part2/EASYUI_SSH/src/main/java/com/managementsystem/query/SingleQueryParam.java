package com.managementsystem.query;

public class SingleQueryParam {
	/**
	 * 操作符号
	 */
	private String OpCode;
	/**
	 * 操作属性
	 */
	private String propertity;
	/**
	 * 操作属性值
	 */
	private Object propertityValue;
	public String getOpCode() {
		return OpCode;
	}
	public void setOpCode(String opCode) {
		OpCode = opCode;
	}
	public String getPropertity() {
		return propertity;
	}
	public void setPropertity(String propertity) {
		this.propertity = propertity;
	}
	public Object getPropertityValue() {
		return propertityValue;
	}
	public void setPropertityValue(Object propertityValue) {
		this.propertityValue = propertityValue;
	}
	
}
