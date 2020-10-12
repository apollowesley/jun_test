package com.zhengjieblog.pocket.service.impl;

import com.auth0.jwt.internal.org.apache.commons.codec.digest.DigestUtils;
import com.zhengjieblog.pocket.entity.User;
import com.zhengjieblog.pocket.repo.UserRepo;
import com.zhengjieblog.pocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User getUserByOpenid(String openid) {
        return userRepo.findByOpenid(openid);
    }

    @Override
    public User createUser(String openid) {

        User user = userRepo.findByOpenid(openid);
        if(user==null){
            user = new User();
            user.setOpenid(openid);
            user.setUuid(UUID.randomUUID().toString().substring(0, 12));
            user.setEnabled(true);
            user.setPassword(DigestUtils.md5Hex(UUID.randomUUID().toString().substring(0, 12)));
            userRepo.save(user);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByID(Long userID) throws Exception {
        User user = userRepo.findOne(userID);
        if(user == null){
            throw new Exception();
        }
        return user;
    }
}
