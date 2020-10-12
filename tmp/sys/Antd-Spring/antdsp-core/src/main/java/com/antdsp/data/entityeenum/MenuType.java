package com.antdsp.data.entityeenum;

public enum MenuType {
	
	MENU("菜单"),
	BUTTON("按钮");
	
	private String value;
	
	MenuType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
