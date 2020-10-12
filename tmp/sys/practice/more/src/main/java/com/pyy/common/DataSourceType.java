package com.pyy.common;

public enum DataSourceType {
	
	DATASOURCE_TYPE_TEST("db_test", "测试"), DATASOURCE_TYPE_DYNAMIC("ds_dynamic", "动态");
	
	private String name;
	
	private String description;
	
	private DataSourceType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
