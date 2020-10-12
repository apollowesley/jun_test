package com.tentcoo.service;

import java.util.List;

import com.tentcoo.entity.User;

public interface UserService {

	

	public boolean addUserInfo(User user);
	
	public boolean updateUserInfo(User user);
	
	public List<User> getAllUserInfo();
	
	public User getUserByUserNo(String sno);
	
	public User getUserByUserCode(String code);
}
