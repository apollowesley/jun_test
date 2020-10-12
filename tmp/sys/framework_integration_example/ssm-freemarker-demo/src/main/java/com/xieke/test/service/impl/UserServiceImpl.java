package com.xieke.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xieke.test.mapper.UserMapper;
import com.xieke.test.model.User;
import com.xieke.test.model.UserExample;
import com.xieke.test.service.UserService;

/**
 * @author xieke
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	public List<User> findAllUser() {

		return userMapper.selectByExample(new UserExample());
	}

}
