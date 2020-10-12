package com.myapp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.dao.UserInfoDao;
import com.myapp.entity.TUser;
import com.myapp.entity.UserInfo;
import com.myapp.service.TUserService;

public class LockTest extends EasymybatisSpringbootApplicationTests {

	@Autowired
	TUserService userService;
	@Autowired
	UserInfoDao userInfoDao;
	
	@Test
	public void testA() {
		UserInfo userInfo = userInfoDao.get(3);
		print(userInfo);
		
	}
	
	@Test
	public void testUpdate() throws InterruptedException {
		final TUser user = userService.get(3);
		user.setUsername("22");
		
		final TUser user2 = userService.get(3);
		user2.setUsername("33");
		
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				userService.updateIgnoreNull(user);
				try {
					Thread.sleep(300); // 暂停，让thread2跑完
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				userService.updateIgnoreNull(user2);
			}
		});
		
		thread1.start(); // 1先拿到锁
		Thread.sleep(10);
		thread2.start();
		
		thread1.join();
		thread2.join();
		
	}
	
}
