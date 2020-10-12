package com.tbc.soa.serialize.json;

import java.util.*;
import java.util.Vector;

public class Person {
	List phoneNumbers;
	User user;
	User[] users;
	Date birthDay;
	Map<String,String> map;
	private Map<String,User> mapStringToUser;
	Person p;
	private MyEnum myEnum;
	public Person() {
//		phoneNumbers.add("abc");
//		user = new User();
//		user.setName("myName");
//		
//		users = new User[] { user };
//		
//		map = new HashMap<String, String>();
//		map.put("key", "value");
	}

	public List getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Person getP() {
		return p;
	}

	public void setP(Person p) {
		this.p = p;
	}

	public void setMapStringToUser(Map<String,User> mapStringToUser) {
		this.mapStringToUser = mapStringToUser;
	}

	public Map<String,User> getMapStringToUser() {
		return mapStringToUser;
	}

	public void setMyEnum(MyEnum myEnum) {
		this.myEnum = myEnum;
	}

	public MyEnum getMyEnum() {
		return myEnum;
	}


}
