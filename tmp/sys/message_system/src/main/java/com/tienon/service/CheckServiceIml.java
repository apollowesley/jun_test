package com.tienon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienon.mapper.LeaveMapper;

@Service
public class CheckServiceIml implements CheckService {
	@Autowired
	private LeaveMapper mapper;

	@Override
	public boolean check(String username) {
		int flag = mapper.check(username);
		if (flag == 1) {
			return false;
		} else
			return true;
	}

}
