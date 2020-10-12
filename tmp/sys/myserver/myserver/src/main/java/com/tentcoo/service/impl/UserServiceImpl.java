package com.tentcoo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tentcoo.dao.UserDao;
import com.tentcoo.entity.User;
import com.tentcoo.service.UserService;


@Transactional
@Service
public class UserServiceImpl implements UserService{

	@Resource
	UserDao userDao;
	
	@Override
	public boolean addUserInfo(User user) {
		// TODO Auto-generated method stub
		return userDao.addUser(user);
	}

	@Override
	public boolean updateUserInfo(User user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

	@Override
	public List<User> getAllUserInfo() {
		
		return userDao.getAllUser();
	}

	@Override
	public User getUserByUserNo(String sno) {
		// TODO Auto-generated method stub
		return userDao.getUserByNo(sno);
	}

	@Override
	public User getUserByUserCode(String code) {
		// TODO Auto-generated method stub
		return userDao.getUserByCode(code);
	}

}
