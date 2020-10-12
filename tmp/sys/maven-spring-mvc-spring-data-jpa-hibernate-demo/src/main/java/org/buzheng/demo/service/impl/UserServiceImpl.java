package org.buzheng.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.buzheng.demo.model.User;
import org.buzheng.demo.repository.UserRepository;
import org.buzheng.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

	@Override
	public User findById(Integer id) {
		return this.userRepository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User update(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public Page<User> findAll(Integer pageNo) {
		
		Pageable pageable = new PageRequest(pageNo, 10);
		
		Page<User> page = this.userRepository.findAll(pageable);
		
		return page;
	}

	
	
}
