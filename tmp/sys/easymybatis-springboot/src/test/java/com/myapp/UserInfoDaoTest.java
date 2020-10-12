package com.myapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.dao.UserInfoDao;
import com.myapp.entity.UserInfo;
import com.myapp.entity.type.UserInfoType;

public class UserInfoDaoTest extends EasymybatisSpringbootApplicationTests {
	
	@Autowired
	UserInfoDao userInfoDao;
	
	@Test
	public void testGet() {
		UserInfo userInfo = userInfoDao.get(3);
		print("枚举字段status：" + userInfo.getStatus().getCode());
		print(userInfo);
	}
	
	@Test
	public void testUpdate() {
		UserInfo userInfo = userInfoDao.get(3);
		// 修改枚举值
		userInfo.setStatus(UserInfoType.INVALID);
		userInfoDao.update(userInfo);
	}
	
	@Test
	public void testSave() {
		UserInfo userInfo = new UserInfo();
		userInfo.setAddress("aa");
		userInfo.setCity("杭州");
		userInfo.setCreateTime(new Date());
		userInfo.setUserId(3);
		// 枚举值
		userInfo.setStatus(UserInfoType.VALID);
		userInfoDao.save(userInfo);
	}
	
	@Test
	public void testInsertMulti() {
		List<UserInfo> users = new ArrayList<>();
		
		for (int i = 0; i < 3; i++) { // 创建3个对象
			UserInfo userInfo = new UserInfo();
			userInfo.setAddress("address" + i);
			userInfo.setCity("杭州");
			userInfo.setCreateTime(new Date());
			userInfo.setUserId(3);
			// 枚举值
			userInfo.setStatus(UserInfoType.VALID);
			users.add(userInfo);
		}
		
		int i = userInfoDao.saveMulti(users); // 返回成功数
		
		System.out.println("saveMulti --> " + i);
	}
}
