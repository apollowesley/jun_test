package org.scau.mystudio.web.service.impl;

import org.scau.mystudio.web.bean.User;
import org.scau.mystudio.web.dao.UserDao;
import org.scau.mystudio.web.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author jinglun
 * @create 2016-09-11 22:44
 */
@Service
public class UserServiceImpl implements UserService {

    //属性userDao，service层调用DAO层完成相关操作
    private UserDao userDao;

    //保存用户注册时的信息
    public void saveUser(User user){
        this.userDao.saveUser(user);
    }

    //用户登录时，用于验证用户的信息
    public User validateUser(String userName, String password){
        return null;
    }

    //属性userDao的get方法
    public UserDao getUserDao(){
        return userDao;
    }
    //属性userDao的set方法
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    /**@Override
    public void regUser(UserForm userForm) throws HibernateException {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userDao.saveObject(user);
    }
    */

}
