package com.laycms.util;

/** 
* @author 作者 zbb: 
* @version 创建时间：2016年7月26日 下午5:58:35 
* 类说明   
*/

public enum EntityStatus {
	DRAFT("草稿"),ISSUE("已发布"),DELETE("已删除");
	
	private String label;
	private EntityStatus(String label){

		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
}
