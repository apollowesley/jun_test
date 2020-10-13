package com.myapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.dao.CrudDao;
import net.oschina.durcframework.easymybatis.query.Query;

public interface TUserDao extends CrudDao<TUser> {
	TUser selectByName(@Param("username")String username);
	
	List<TUser> findByMap(@Param("map") Map<String, Object> map);
	TUser getByMap(@Param("map") Map<String, Object> map);
	
	List<TUser> findJoinPage(Query query);
}