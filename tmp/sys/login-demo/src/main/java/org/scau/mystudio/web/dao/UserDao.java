package org.scau.mystudio.web.dao;

import org.scau.mystudio.web.bean.User;
import org.springframework.stereotype.Repository;

/**
 * @author jinglun
 * @create 2016-09-10 11:25
 */
@Repository
public interface UserDao {

    //保存用户注册信息
    public void saveUser(User user);

    //用户登录时验证用户信息
    public User validateUser(String userName, String password);

}
