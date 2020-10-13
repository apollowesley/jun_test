package com.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dao.TUserDao;
import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.Sort;
import net.oschina.durcframework.easymybatis.query.annotation.ValueField;
import net.oschina.durcframework.easymybatis.query.param.PageParam;
import net.oschina.durcframework.easymybatis.query.param.PageSortParam;

@RestController
public class UserSchController2 {

	@Autowired
	private TUserDao dao;
	
	// http://localhost:8080/sch?username=张三
	@GetMapping("sch")
	public List<TUser> sch(String username) {
		Query query = new Query();
		query.eq("username", username);
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/sch2?username=张三
	@GetMapping("sch2")
	public List<TUser> sch2(String username) {
		Query query = new Query();
		query.eq("username", username).gt("money", 100);
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/sch3?username=张三&pageIndex=1&pageSize=5
	@GetMapping("sch3")
	public List<TUser> sch3(String username,PageParam param) {
		Query query = param.toQuery();
		query.eq("username", username);
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/sch4
	@GetMapping("sch4")
	public List<TUser> sch4() {
		Query query = new Query();
		query.addSort("money", Sort.DESC) // 按金额降序
			.setPage(1, 3);
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/sch5?username=张三
	@GetMapping("sch5")
	public List<TUser> sch5(UserParam userParam) {
		Query query = userParam.toQuery();
		query.eq("username", userParam.getUsername());
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/sch6?username=张三
	@GetMapping("sch6")
	public List<TUser> sch6(UserParam userParam) {
		Query query = userParam.toQuery();
		List<TUser> list = dao.find(query);
		return list;
	}
	
	public static class UserParam extends PageSortParam {
		private String username;
		// 这里定义了注解，当执行userParam.toQuery()方法的时候会自动把username值添加到条件当中。
		// 即执行了query.eq("username",username);操作
		@ValueField(column="username")
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}
}
