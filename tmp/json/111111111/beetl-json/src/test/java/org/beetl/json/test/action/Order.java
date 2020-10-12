package org.beetl.json.test.action;

import java.util.ArrayList;
import java.util.List;

public class Order {
	int id ;
	String name;
	List<OrderDetail> details= new ArrayList<OrderDetail>();
    public Order(int id,String name){
    	this.id = id;
    	this.name = name;
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
	public List<OrderDetail> getDetails() {
		return details;
	}
	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}
	
}
