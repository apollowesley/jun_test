package com.cnit1818.demo.service;

import com.cnit1818.demo.entity.UserInfo;

import java.util.List;

/**
 * Created by mayong on 2016/4/23.
 */
public interface UserInfoService {

    public Long insert(UserInfo userInfo);

    public UserInfo findById(Long id);

    public List<UserInfo> findByConf(UserInfo userInfo);

    public List<UserInfo> findListByConf(UserInfo userInfo);

    public Integer updateById(UserInfo userInfo);
}
