package com.zhengjieblog.pocket.service;

import com.zhengjieblog.pocket.entity.User;

import java.util.List;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
public interface UserService {

    /**
     * 根据openid 获取user信息
     * @param openid
     * @return
     */
    User getUserByOpenid(String openid);

    /**
     * 新增
     * @param openid
     * @return
     */
    User createUser(String openid);

    /**
     * 查询全部
     * @return
     */
    List<User> findAll();

    /**
     * 根据userID 获取user信息
     * @param userID
     * @return
     * @throws Exception
     */
    User getUserByID(Long userID) throws Exception;
}
