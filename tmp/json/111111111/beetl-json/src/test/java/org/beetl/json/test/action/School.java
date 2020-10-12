package org.beetl.json.test.action;

import java.util.ArrayList;
import java.util.List;

public class School {
	private String name = "小学";
	private Teacher master = new Teacher(1,"joelli");
	List<Teacher> list  = new ArrayList<Teacher>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Teacher getMaster() {
		return master;
	}
	public void setMaster(Teacher master) {
		this.master = master;
	}
	public List<Teacher> getList() {
		return list;
	}
	public void setList(List<Teacher> list) {
		this.list = list;
	}
	
}
