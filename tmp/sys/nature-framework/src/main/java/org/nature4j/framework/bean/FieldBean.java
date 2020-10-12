package org.nature4j.framework.bean;

import org.nature4j.framework.enums.Types;

public class FieldBean {
	private String fieldName;
	private String columnName;
	private Types columnType;
	private String columnLength;
	private String defValue;
	private boolean ignore;
	
	public FieldBean() {
	}
	
	public FieldBean(String fieldName, String columnName, Types columnType, String columnLength, String defValue,
			boolean ignore) {
		this.fieldName = fieldName;
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnLength = columnLength;
		this.defValue = defValue;
		this.ignore = ignore;
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
	public boolean isIgnore() {
		return ignore;
	}
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}


}
