package com.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dao.TUserDao;
import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.PageInfo;
import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.param.PageParam;
import net.oschina.durcframework.easymybatis.util.QueryUtils;

@RestController
public class UserSchController {

	@Autowired
	private TUserDao dao;
	
	// http://localhost:8080/page1?pageIndex=1&pageSize=10
	@GetMapping("page1")
	public List<TUser> page1(int pageIndex,int pageSize) {
		Query query = new Query();
		query.setPage(pageIndex, pageSize);
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/page2?pageIndex=1&pageSize=10
	@GetMapping("page2")
	public List<TUser> page2(PageParam param) {
		Query query = param.toQuery();
		List<TUser> list = dao.find(query);
		return list;
	}
	
	// http://localhost:8080/page3?pageIndex=1&pageSize=10
	@GetMapping("page3")
	public Map<String,Object> page3(PageParam param) {
		Query query = param.toQuery();
		List<TUser> list = dao.find(query);
		long total = dao.countTotal(query);
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("total", total);
		
		return result;
	}
	
	// http://localhost:8080/page4?pageIndex=1&pageSize=10
	@GetMapping("page4")
	public PageInfo<TUser> page4(PageParam param) {
		PageInfo<TUser> result = QueryUtils.query(dao, param);
		return result;
	}
}
