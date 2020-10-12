/**   
 * BaseDaoTest.java
 *
 * @author sxjun
 * @date 2014-7-20 下午10:19:01 
 * @verion 0.1.0
 */
package com.mini.example;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mini.example.bean.User;
import com.mini.example.service.DynamicService;


/**
 * 
 * @author sxjun
 * @date 2014-7-20 下午10:19:01
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../applicationContext.xml")
public class DynamicExample {

	@Autowired
	private DynamicService dynamicService;

	/**
	 * 动态数据源切换测试
	 * 配置注解@TargetDataSource("value")
	 * value对应的动态数据源
	 */
	@Test
	public void testInsert() {
		User user = new User();
        user.setCreateTime(new Date()).setId("iiiiiiiiiiiiiiii").setName("username") .setType(new Random().nextInt(5));
        dynamicService.insert(user);
	}
}
