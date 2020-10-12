package com.tienon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienon.config.SecurityInterceptor;
import com.tienon.mapper.LeaveMapper;

@Service
public class LeaveServiceImpl implements LeaveService {
	@Autowired
	private LeaveMapper mapper;

	@Override
	public boolean leave(String username, String topic, String message) {
		
		int a = mapper.leave(username, topic, message);
		if (a > 0)
			return true;
		return false;
	}

}
