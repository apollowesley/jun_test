package com.yonge.crud.core.db.model;

public class Column {

	/** 字段名称 */
	private String fieldName;

	/** 是否是主键 */
	private boolean isPrimaryKey;

	/** 属性名称 */
	private String propertyName;

	/** JDBC类型 名称 */
	private String jdbcTypeName;

	/** java类型 */
	private String javaTypeName;

	/** 数据类型 */
	private int dataType;

	/** 字段长度 */
	private int length;

	/** 是否可以为空 */
	private boolean nullable;

	private int scale;

	/** 描述 */
	private String remarks;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getJdbcTypeName() {
		return jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName) {
		this.jdbcTypeName = jdbcTypeName;
	}

	public String getJavaTypeName() {
		return javaTypeName;
	}

	public void setJavaTypeName(String javaTypeName) {
		this.javaTypeName = javaTypeName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("fieldName = " + fieldName)
				.append(" isPrimaryKey = " + isPrimaryKey)
				.append(" propertyName = " + propertyName)
				.append(" jdbcTypeName = " + jdbcTypeName)
				.append(" javaTypeName = " + javaTypeName)
				.append(" dataType = " + dataType)
				.append(" length = " + length)
				.append(" nullable = " + nullable).append(" scale = " + scale)
				.append(" remarks = " + remarks);
		return sb.toString();
	}
}
