package org.neuedu.fly.mapper;

import org.apache.ibatis.annotations.Param;
import org.neuedu.fly.entity.User;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:34
  **/
public interface UserMapper {

    //参数超过3个以上就得使用对象
    //在MyBatis中，参数两个以上就得加注解
    User login(@Param("logName") String logName, @Param("logPwd") String logPwd);

    boolean register(User user);

    //查询手机号是否唯一
    boolean isUniqueTel(String tel);

}