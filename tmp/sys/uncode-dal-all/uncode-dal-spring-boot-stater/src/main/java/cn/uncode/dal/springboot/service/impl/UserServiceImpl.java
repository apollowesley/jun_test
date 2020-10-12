package cn.uncode.dal.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.uncode.dal.core.BaseDAL;
import cn.uncode.dal.descriptor.QueryResult;
import cn.uncode.dal.springboot.dto.User;
import cn.uncode.dal.springboot.mapper.UserMapper;
import cn.uncode.dal.springboot.service.UserService;

/**
 * Created by KevinBlandy on 2017/2/28 15:10
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Autowired
	private BaseDAL baseDAL;
	
	
	@Transactional
	public Integer create(User userEntity){
		return this.userMapper.create(userEntity);
	}

	@Override
	public User users() {
		return this.userMapper.users();
	}

	@Override
	public User get(int id) {
		QueryResult queryResult = baseDAL.selectByPrimaryKey("user", 1);
		return queryResult.as(User.class);
	}
}
