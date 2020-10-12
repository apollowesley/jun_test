package com.tentcoo.dao;

import java.util.List;

import com.tentcoo.entity.User;



public interface UserDao {

	
	public boolean addUser(User user);
	
	public boolean updateUser(User user);
	
	public List<User> getAllUser();
	
	public User getUserByNo(String sno);
	
	public User getUserByCode(String code);
	
}
