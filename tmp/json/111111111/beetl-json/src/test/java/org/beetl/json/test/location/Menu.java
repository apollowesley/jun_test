package org.beetl.json.test.location;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	int id = 1;
	MenuItem item =new MenuItem();
	List<MenuItem> list = new ArrayList<MenuItem>();
	public Menu(){
		list.add(new MenuItem());
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MenuItem getItem() {
		return item;
	}
	public void setItem(MenuItem item) {
		this.item = item;
	}
	public List<MenuItem> getList() {
		return list;
	}
	public void setList(List<MenuItem> list) {
		this.list = list;
	}
	
	
	
}
