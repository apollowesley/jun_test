package com.evil.pojo;

import java.util.Date;




public class ExamType extends BaseEntity {
	private static final long serialVersionUID = -1109381245812601721L;
	private String id;
	private String name;
	private String typeDescribe;  //¿‡±√Ë ˆ
	private Date addTime=new Date();
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id=id;
		
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
