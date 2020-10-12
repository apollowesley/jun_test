package org.beetl.json.test.one2many;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	int id;
	String name;
	List<Product> list = new ArrayList<Product>();
	public Customer(){
		id = 1;
		name="joelli";
		Product p1 = new Product();
		p1.setCode("01");
		p1.setTotal(12);
		list.add(p1);
		
		 p1 = new Product();
		p1.setCode("01");
		p1.setTotal(12);
		list.add(p1);
		
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
	public List<Product> getList() {
		return list;
	}
	public void setList(List<Product> list) {
		this.list = list;
	}
	
}
