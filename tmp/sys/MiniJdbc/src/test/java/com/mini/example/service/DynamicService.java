package com.mini.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mini.example.bean.User;
import com.mini.jdbc.dao.MiniDao;
import com.mini.jdbc.dynamic.TargetDataSource;

@Service
public class DynamicService {

	@Autowired
	private MiniDao miniDao;
	
	@TargetDataSource("key2")
	public void insert(User user){
		miniDao.insert(user);
	}
}
