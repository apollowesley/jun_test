package com.xieke.test.service;

import com.xieke.test.dao.UserDao;
import com.xieke.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findUserByName(String name){
        User user = null;
        try{
            user = userDao.findUserByName(name);
        } catch (Exception e){

        }
        return user;
    }

    public User findUserByNameAndPassword(String name, String  password){
        User user = null;
        try{
            User u = new User();
            u.setName(name);
            u.setPassword(password);
            user = userDao.findUserByNameAndPassword(u);
        } catch (Exception e){

        }
        return user;
    }

    @Transactional
    public void saveUser ( ){
        userDao.insertUser("a123456789", "123456789");
        // int n = 1/0;
        // userDao.insertUser("b123456789", "123456789");
    }
}