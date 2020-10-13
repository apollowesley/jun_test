package com.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.common.bean.ResultBean;
import com.myapp.common.bean.Results;
import com.myapp.entity.TUser;
import com.myapp.entity.TUserSch;
import com.myapp.service.TUserService;

import net.oschina.durcframework.easymybatis.PageResult;
import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.support.PageEasyui;

@RestController
public class TUserController {

	@Autowired
	private TUserService tUserService;
	
	// TUserSch里面存放接收参数，根据注解生成query
	// http://localhost:8080/findUser.do?stateSch=1
	@GetMapping(path="findUser.do")
	public PageResult<TUser> findUser(TUserSch userSch) {
		Query query = userSch.toQuery();
		PageResult<TUser> page = tUserService.query(query);
		return page;
	}
	
	@GetMapping(path="findUserBean.do")
	public PageResult<TUser> findUser(TUser user) {
		Query query = Query.build(user);
		PageResult<TUser> page = tUserService.query(query);
		return page;
	}
	
	// http://localhost:8080/listUser
	@GetMapping(path="listUser.do")
	public PageResult<TUser> listUser() {
		Query query = Query.build().eq("state", 1);
		PageEasyui page = tUserService.query(query, PageEasyui.class);
		return page;
	}
	
	// http://localhost:8080/listUser2
	@GetMapping(path="listUser2.do")
	public ResultBean listUser2() {
		Query query = Query.build().eq("state", 1);
		PageEasyui page = tUserService.query(query,PageEasyui.class);
		return Results.success(page);
	}
	
	@GetMapping(path="updateUserTran.do")
	public ResultBean updateUserTran() {
		TUser user = tUserService.get(3);
		tUserService.updateTran(user);
		return Results.success();
	}
	
	@GetMapping(path="selectByName.do")
	public ResultBean selectByName() {
		TUser user = tUserService.selectByName("张三");
		return Results.success(user);
	}
	
}
