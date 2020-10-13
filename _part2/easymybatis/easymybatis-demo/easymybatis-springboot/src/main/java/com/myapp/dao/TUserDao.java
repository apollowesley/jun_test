package com.myapp.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.dao.CrudDao;

public interface TUserDao extends CrudDao<TUser> {
	
	@Select("select * from t_user where username=#{username} limit 1")
	@ResultMap(baseResultMap) // 最好加上，如果Entity中定义了枚举属性，必须要加
	TUser selectByName(@Param("username")String username);
	
	@Select("select * from t_user where id=#{id}")
	@ResultMap(baseResultMap) // 最好加上，如果Entity中定义了枚举属性，必须要加
	TUser selectById(@Param("id") int id);
}