package com.baomidou.kisso.oauth2demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.kisso.common.encrypt.SaltEncoder;
import com.baomidou.kisso.oauth2demo.dao.UserDao;
import com.baomidou.kisso.oauth2demo.entity.User;
import com.baomidou.kisso.oauth2demo.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;


	/**
	 * 创建用户
	 * @param user
	 */
	public User createUser( User user ) {
		//加密密码
		user.setPassword(SaltEncoder.md5SaltEncode(user.getUsername(), user.getPassword()));
		return userDao.createUser(user);
	}


	@Override
	public User updateUser( User user ) {
		return userDao.updateUser(user);
	}


	@Override
	public void deleteUser( Long userId ) {
		userDao.deleteUser(userId);
	}


	/**
	 * 修改密码
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword( Long userId, String newPassword ) {
		User user = userDao.findOne(userId);
		user.setPassword(SaltEncoder.md5SaltEncode(user.getUsername(), user.getPassword()));
		userDao.updateUser(user);
	}


	@Override
	public User findOne( Long userId ) {
		return userDao.findOne(userId);
	}


	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}


	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findByUsername( String username ) {
		return userDao.findByUsername(username);
	}


	/**
	 * 验证登录
	 * @param username 用户名
	 * @param password 密码
	 * @param encryptpwd 加密后的密码
	 * @return
	 */
	public boolean checkUser( String username, String password, String encryptpwd ) {
		return SaltEncoder.md5SaltValid(username, encryptpwd, password);
	}


}
