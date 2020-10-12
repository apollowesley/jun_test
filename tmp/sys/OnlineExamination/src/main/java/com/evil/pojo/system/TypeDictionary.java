package com.evil.pojo.system;


import java.util.Date;

import com.evil.pojo.BaseEntity;

public class TypeDictionary extends BaseEntity {
	private static final long serialVersionUID = 7658663932811763452L;
	
	public final static int PAPER_TYPE=0;               //试卷类型
	public final static int QUESTION_TYPE=1;            //问题类型
	
	
	private String id; //id 
	private int type; //字典类型
	private int value;  //字段的值
	private String name;  //字段的名字
	private Date addTime=new Date();  //添加时间
	private String typeDescribe; //字段的描述

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getTypeDescribe() {
		return typeDescribe;
	}

	public void setTypeDescribe(String typeDescribe) {
		this.typeDescribe = typeDescribe;
	}
	
	
	
	
	

}
