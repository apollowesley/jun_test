package org.beetl.json.performance;

import org.beetl.json.annotation.Json;
import org.beetl.json.annotation.JsonPolicy;


public class Org {
	int id = 3;
	Depart dept ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Depart getDept() {
		return dept;
	}
	public void setDept(Depart dept) {
		this.dept = dept;
	}
	
}
