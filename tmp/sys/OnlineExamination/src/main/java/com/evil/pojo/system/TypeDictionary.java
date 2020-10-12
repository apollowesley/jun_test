package com.evil.pojo.system;


import java.util.Date;

import com.evil.pojo.BaseEntity;

public class TypeDictionary extends BaseEntity {
	private static final long serialVersionUID = 7658663932811763452L;
	
	public final static int PAPER_TYPE=0;               //�Ծ�����
	public final static int QUESTION_TYPE=1;            //��������
	
	
	private String id; //id 
	private int type; //�ֵ�����
	private int value;  //�ֶε�ֵ
	private String name;  //�ֶε�����
	private Date addTime=new Date();  //���ʱ��
	private String typeDescribe; //�ֶε�����

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
