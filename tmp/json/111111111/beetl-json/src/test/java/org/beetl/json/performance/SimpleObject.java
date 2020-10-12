package org.beetl.json.performance;

import java.util.Date;

public class SimpleObject {
	String name = "a";
	String desc = "b";
	int age = 123;
	boolean old = true ;
//	Date date = new Date();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
//	public Date getDate() {
//		return date;
//	}
//	public void setDate(Date date) {
//		this.date = date;
//	}
	public boolean isOld() {
		return old;
	}
	public void setOld(boolean old) {
		this.old = old;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
