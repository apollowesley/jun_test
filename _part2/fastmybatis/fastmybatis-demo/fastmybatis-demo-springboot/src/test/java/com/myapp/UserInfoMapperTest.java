package com.myapp;

import com.myapp.dao.UserInfoMapper;
import com.myapp.entity.TUser;
import com.myapp.entity.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tanghc
 */
public class UserInfoMapperTest extends FastmybatisSpringbootApplicationTests {

	@Autowired
	UserInfoMapper mapper;


	@Test
	public void testGet() {
		UserInfo userInfo = mapper.getById(2);
		System.out.println(userInfo.getAddress());
		// 这里触发懒加载，将会执行
		// SELECT t.`id` , t.`username` , t.`state` , t.`isdel` , t.`remark` , t.`add_time` , t.`money` , t.`left_money` FROM `t_user` t WHERE `id` = ? AND t.isdel = 0 LIMIT 1 
		// 可将下面两句注释查看sql执行情况
		TUser user = userInfo.getUser();

		System.out.println("延迟数据：" + user);
	}

	@Test
	public void testUpdate() {
		UserInfo userInfo = mapper.getById(2);
		userInfo.setAddress("北京" + System.currentTimeMillis());
		int i = mapper.update(userInfo);
		System.out.println("update i:" + i);
	}
}

