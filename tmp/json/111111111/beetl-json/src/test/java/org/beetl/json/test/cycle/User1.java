package org.beetl.json.test.cycle;

import java.util.ArrayList;
import java.util.List;

public class User1 {
	private int id ;
	List list = new ArrayList();
	
	public User1(int id){
		this.id = id ;
		list.add(this);
		list.add(this);
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
}
