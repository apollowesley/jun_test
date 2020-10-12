package cn.afterturn.dao.test.examples.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.afterturn.dao.test.examples.dao.user.UserDao;

@Service
public class UserServer {
	
	@Autowired
	private UserDao userDao;

	public List<Map<String, Object>> listUserByAge(int i) {
		return userDao.listUserByAge(i);
	}

	public void updateUserBirthday(String string, Integer age, Date date) {
		userDao.updateUserBirthday(string, age, date);
	}

}
