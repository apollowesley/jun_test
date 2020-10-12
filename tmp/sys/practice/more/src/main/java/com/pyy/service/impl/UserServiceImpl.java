package com.pyy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyy.dao.BookMapper;
import com.pyy.dao.UserMapper;
import com.pyy.model.Book;
import com.pyy.model.User;
import com.pyy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BookMapper bookMapper;
	
	@Override
	public List<User> getUserList() {
		
		return userMapper.selectAllUsers();
	}
	
	
	public List<Book> getBookList() {
		
		return bookMapper.selectAllBooks();
	}
 
}
