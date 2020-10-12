package com.myapp.dao;

import org.springframework.stereotype.Repository;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.dao.ext.CrudDao;

/**
 * 这种Dao是以继承方式
 * @author tanghc
 *
 */
@Repository
public class UserDao2 extends CrudDao<TUser> {

}
