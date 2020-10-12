package org.neuedu.fly.dao;

import org.neuedu.fly.entity.User;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/21-9:54
 **/
public interface IUserDao {

    boolean register(User user);
    boolean findByTel(String tel);

}
