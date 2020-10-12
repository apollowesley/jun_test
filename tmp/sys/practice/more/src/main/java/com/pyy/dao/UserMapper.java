package com.pyy.dao;

import java.util.List;

import com.pyy.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    /**
     * 
     * @return
     */
    List<User> selectAllUsers();

}