package com.tbc.soa.serialize.json;

import java.util.List;

public class Member {
	public List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	public List getUserList(Integer i) {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
}
