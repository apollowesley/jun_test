package com.myapp.dao;

import org.springframework.stereotype.Repository;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.dao.ext.CrudDao;

@Repository
public class UserDao extends CrudDao<TUser> {

}
