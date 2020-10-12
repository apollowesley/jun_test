package com.myapp.dao;

import com.myapp.entity.TUser;

import net.oschina.durcframework.easymybatis.dao.Dao;

// 这个类不是必须的，目的为了测试跟其他Mapper是否兼容
// 确保传统方式跟框架能够一起使用
public interface UserDao extends Dao<TUser>{

	TUser getById(int id);
}
