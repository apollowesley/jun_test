package com.tienon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienon.mapper.LeaveMapper;
import com.tienon.util.MD5Util;

@Service
public class RegistServiceIm implements RegistService {

	@Autowired
	private LeaveMapper mapper;

	@Override
	public boolean regist(String username, String password) {
		String pwd=MD5Util.md5(password);
		int a = mapper.regist(username, pwd);
		if (a > 0)
			return true;
		return false;
	}

}
