package org.beetl.json.test.action;

import java.util.ArrayList;
import java.util.List;

public class Department {
	int id ;
//	String name;
	List<SysUser> users = new ArrayList<SysUser>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
	public List<SysUser> getUsers() {
		return users;
	}
	public void setUsers(List<SysUser> users) {
		this.users = users;
	}
	
	
}
