package com.tienon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienon.mapper.LeaveMapper;
import com.tienon.util.MD5Util;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LeaveMapper mapper;

	@Override
	public boolean login(String username, String password) {
		String pwd=MD5Util.md5(password);
		Integer flag = mapper.login(username, pwd);
		if (flag != null) {
			return true;
		} else {
			return false;
		}
	}

}
