package com.xieke.test.dao;

import com.xieke.test.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(@Param("name") String name);

    @Insert("INSERT INTO user(name, password) VALUES(#{name}, #{password})")
    int insertUser(@Param("name") String name, @Param("password") String password);

    User findUserByNameAndPassword (User  user);
}