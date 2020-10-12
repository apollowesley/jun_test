package com.antdsp.utils.excelutil;

import org.springframework.util.StringUtils;

public class Column {

	/**
	 * 	列头显示文字
	 */
	private String title;
	
	/**
	 * 	对应的实体类字段
	 */
	private String field;
	
	private ColumnRender render;
	
	private String getterName;
	
	public Column(String title, String field, ColumnRender render) {
		this.title = title;
		this.field = field;
		this.render = render;
		this.setGetterName(field);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public ColumnRender getRender() {
		return render;
	}

	public void setRender(ColumnRender render) {
		this.render = render;
	}

	public String getGetterName() {
		return getterName;
	}

	private void setGetterName(String getterName) {
		if(!StringUtils.isEmpty(getterName)) {

			this.getterName = "get"+field.substring(0, 1).toUpperCase()+field.substring(1,field.length());
		}
	}
	
	
}
