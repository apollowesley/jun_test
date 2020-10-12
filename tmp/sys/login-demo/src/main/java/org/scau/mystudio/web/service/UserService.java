package org.scau.mystudio.web.service;

import org.scau.mystudio.web.bean.User;

/**
 * @author jinglun
 * @create 2016-09-10 11:26
 */

public interface UserService {

    //用户注册时，保存用户的信息
    public void saveUser(User user);

    //用户登录时，验证用户的信息
    public User validateUser(String userName,String password);


}
