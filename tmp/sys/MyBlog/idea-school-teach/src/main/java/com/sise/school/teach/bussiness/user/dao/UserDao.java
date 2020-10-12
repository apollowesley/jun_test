package com.sise.school.teach.bussiness.user.dao;

import com.sise.school.teach.bussiness.user.po.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 模拟查询数据库
 *
 * @author idea
 * @data 2018/9/22
 */
@Slf4j
@Component
public class UserDao {

    /**
     * 模拟插入
     *
     * @param userPO
     */
    public void insert(UserPO userPO) {
        log.info("【营销用户】插入用户信息，userPO{}", userPO);
    }

    /**
     * 查询用户
     *
     * @param openid
     * @return
     */
    public UserPO selectOne(String openid) {
        log.info("【营销用户】查询用户信息，openid{} ", openid);
        UserPO userPO = new UserPO();
        userPO.setUsername("username");
        userPO.setPassword("password");
        userPO.setSalt("salt");
        return userPO;
    }
}
