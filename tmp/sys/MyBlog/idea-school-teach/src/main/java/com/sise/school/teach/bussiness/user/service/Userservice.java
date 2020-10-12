package com.sise.school.teach.bussiness.user.service;

import com.sise.school.teach.bussiness.user.dao.UserDao;
import com.sise.school.teach.bussiness.user.po.UserPO;
import com.sise.school.teach.bussiness.user.vo.req.UserReqVO;
import com.sise.school.teach.bussiness.user.vo.resp.UserRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author idea
 * @data 2018/9/22
 */
@Service
@Slf4j
public class Userservice {

    @Autowired
    private UserDao userDao;

    public String userValidate(String auth) {
        if ("auth".equals(auth)) {
            //模拟从redis中取出openid
            return "openid";
        } else {
            return null;
        }
    }

    /**
     * 插入用户信息
     *
     * @param userReqVO
     */
    public void insert(UserReqVO userReqVO) {
        //只是demo，这里不做参数校验了
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userReqVO, userPO);
        userDao.insert(userPO);
    }


    /**
     * 查询用户信息
     *
     * @param openid
     */
    public UserRespVO selectOne(String openid) {
        //只是demo，这里不做参数校验了
        UserPO userPO = userDao.selectOne(openid);
        UserRespVO userRespVO = new UserRespVO();
        BeanUtils.copyProperties(userPO, userRespVO);
        return userRespVO;
    }

    public void login(String username, String password) {
        log.info("【营销用户】登录传入参数为：{}", username + " " + password);
    }
}
