package com.cnit1818.demo.service.impl;

import com.cnit1818.base.SimpleGenericDAO;
import com.cnit1818.demo.entity.UserInfo;
import com.cnit1818.demo.service.UserInfoService;
import com.cnit1818.utils.Page;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mayong on 2016/4/23.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private SimpleGenericDAO<UserInfo, Long> userInfoDao;


    @Override
    public Long insert(UserInfo userInfo) {
        Long pk = null;
        try {
            pk = userInfoDao.insert(userInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pk;
    }

    public UserInfo findById(Long id) {
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = userInfoDao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public List<UserInfo> findByConf(UserInfo userInfo) {
        List<UserInfo> list = null;
        try {
            Page page = new Page();
            page.setCond(userInfo);
            list = userInfoDao.pageQueryByCond(page);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<UserInfo> findListByConf(UserInfo userInfo) {
        List<UserInfo> list = null;
        try {
            list = userInfoDao.findListByCond(userInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Integer updateById(UserInfo userInfo) {
        Integer count = null;
        try {
            count = userInfoDao.updateById(userInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Autowired
    public void setDpClient(SqlMapClient dpClient) {
        this.userInfoDao = new SimpleGenericDAO<UserInfo, Long>(dpClient, UserInfo.class);
    }

}
