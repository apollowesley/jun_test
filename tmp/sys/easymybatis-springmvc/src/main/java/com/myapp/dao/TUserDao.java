package com.myapp.dao;

import org.apache.ibatis.annotations.Param;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.dao.CrudDao;

public interface TUserDao extends CrudDao<TUser> {
	TUser selectByName(@Param("username")String username);
}