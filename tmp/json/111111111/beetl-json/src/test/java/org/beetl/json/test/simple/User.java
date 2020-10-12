package org.beetl.json.test.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class User {
	private int id ;	
	private String name = null;
	
	private User wife = null;
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
	
	public User getWife() {
		return wife;
	}
	public void setWife(User wife) {
		this.wife = wife;
	}
	public static User getTestUser(){
		User user = new User();
		user.setName("joelli");
		user.setId(1);
		user.wife = new User();
		user.wife.id=2;
		user.wife.name="lucy";
		return user;
	}
	
	
	public static List<User> getTestUsers(){
		List list = new ArrayList<User>();
		User user = new User();
		user.setName("joelli");
		user.setId(1);
		list.add(user);
		user = new User();
		user.setName("lucy");
		user.setId(2);
		list.add(user);
		
		user = new User();
		user.setName("bear");
		user.setId(3);
		list.add(user);
		
		return list;
	}
	
	public static Map<String,User> getTestUserMap(){
		Map map = new TreeMap<String,User>();
		User user = new User();
		user.setName("joelli");
		user.setId(1);
		map.put(user.getName(), user);
		user = new User();
		user.setName("lucy");
		user.setId(2);
		map.put(user.getName(), user);
		
		user = new User();
		user.setName("bear");
		user.setId(3);
		map.put(user.getName(), user);
		
		return map;
	}
	
}
