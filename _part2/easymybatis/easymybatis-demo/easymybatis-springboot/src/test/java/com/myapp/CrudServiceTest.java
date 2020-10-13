package com.myapp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.entity.TUser;
import com.myapp.service.TUserService;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.support.PageEasyui;

public class CrudServiceTest extends EasymybatisSpringbootApplicationTests {

	@Autowired
	private TUserService tUserService;
	
	@Test
	public void testGet() {
		TUser user = tUserService.get(3);
		
		print(user);
	}
	
	@Test
	public void testQuery() {
		Query query = Query.build().eq("state", 1);
		PageEasyui page = tUserService.query(query, PageEasyui.class);
		printJson(page);
	}
}
