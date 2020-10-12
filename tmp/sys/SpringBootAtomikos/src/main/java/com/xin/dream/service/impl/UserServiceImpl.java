package com.xin.dream.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xin.dream.one.dao.OneUserMapper;
import com.xin.dream.pojo.User;
import com.xin.dream.service.UserService;
import com.xin.dream.two.dao.TwoUserMapper;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	public OneUserMapper oneUserDao;
	@Autowired
	public TwoUserMapper twoUserDao;
	@Override
	public User selectByPrimaryKey(Long id) {
		User obj=new User();
		obj.setLoginName("bianpeng");
		obj.setPassword("123");
		obj.setLevel(1l);
		obj.setNote("管理员");
		oneUserDao.insertSelective(obj);
		System.out.println("===========123123213213==============");
		obj.setLoginName(null);
		twoUserDao.insertSelective(obj);
		System.out.println("===========aaaaaaaaaaaaaaaaa==============");
		return null;
	}

}
