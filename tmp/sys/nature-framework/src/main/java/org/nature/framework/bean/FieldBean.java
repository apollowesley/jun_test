package org.nature.framework.bean;

import org.nature.framework.enums.Types;

public class FieldBean {
	private String fieldName;
	private String columnName;
	private Types columnType;
	private String columnLength;
	private String defValue;
	
	public FieldBean() {
	}
	public FieldBean(String fieldName, String columnName, Types columnType, String columnLength, String defValue) {
		this.fieldName = fieldName;
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnLength = columnLength;
		this.defValue = defValue;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Types getColumnType() {
		return columnType;
	}
	public void setColumnType(Types columnType) {
		this.columnType = columnType;
	}
	public String getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}


}
