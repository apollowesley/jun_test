package com.zhoux.freemarker.bean;
/**
 * 
 * @author zhoux
 * @Date Mar 31, 2017
 * @Email zhoux@souche.com
 * @Desc 字段属性bean
 */
public class TableColumnBean {

	private String columnName;
	
	private String columnType;
	
	private String javaName;
	
	private String javaType;
	
	private String methodName;
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public TableColumnBean() {
		// TODO Auto-generated constructor stub
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		
		this.javaName = javaName;
		this.methodName=String.valueOf(javaName.charAt(0)).toUpperCase()+javaName.substring(1);

	}
	
	
}
