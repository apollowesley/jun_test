package org.buzheng.demo.service;

import java.util.List;

import org.buzheng.demo.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
	
	public User findById(Integer id);
	
	public List<User> findAll();
	
	public User update(User user);
	
	public Page<User> findAll(Integer pageNo);
	
}
