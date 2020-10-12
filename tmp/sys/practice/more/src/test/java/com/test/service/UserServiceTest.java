package com.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pyy.dao.UserMapper;
import com.pyy.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application*.xml"})
public class UserServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Autowired
	private UserMapper userMapper;
	@Test
	public void test() {
		
		List<User> userList = userMapper.selectAllUsers();
		
		for(User user : userList) {
			logger.info("id is {}", user.getId());
			logger.info("user name is {}", user.getUserName());
			logger.info("age is {}", user.getAge());
		}
	}

}
