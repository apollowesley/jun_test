package org.beetl.json.performance;

import java.util.ArrayList;
import java.util.List;

public class One2ManyObject {
	private int id = 10;
	String name="cccc";
	List<SimpleObject> list = null;
	public One2ManyObject(){
		 list = new ArrayList();
		for(int i=0;i<10;i++){
			list.add(new SimpleObject());
		}
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SimpleObject> getList() {
		return list;
	}
	public void setList(List<SimpleObject> list) {
		this.list = list;
	}
	
	
}
