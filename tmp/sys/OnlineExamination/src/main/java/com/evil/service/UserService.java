package com.evil.service;

import java.util.List;
import java.util.Map;

import com.evil.pojo.User;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface UserService extends BaseService<User> {
	//登录校验那
	public User LoginCheck(String email,String password);
	//获取所有学生的列表
	public List<User> findAll();

	//根据学生的邮箱获取学生
	public User findByEmail(String email);
	/**
	 * 判断邮箱是否被占用 
	 */
	public boolean isRegisted(String emailAddress);
	
	/**
	 * 根据分页信息条件查询user
	 * @param page 分页信息
	 * @param map 参数集合
	 * @return
	 */
	PageResult findPageUsers(Page page, Map<String, Object> map,String...sortfields);
	
	/**
	 *批量输出用户
	 */
	public void batchDeleteUsers(String[] strSplit);
	
	/**
	 *启用/禁用户
	 */
	public void toggleUserStatus(User user);	
		

}
