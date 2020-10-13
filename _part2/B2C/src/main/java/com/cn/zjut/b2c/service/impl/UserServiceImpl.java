package com.cn.zjut.b2c.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.zjut.b2c.dao.UserDao;
import com.cn.zjut.b2c.pojo.User;
import com.cn.zjut.b2c.service.UserServiceInter;

@Service("userService")
public class UserServiceImpl implements UserServiceInter {
	@Resource
	private UserDao userDao;
	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}

}
