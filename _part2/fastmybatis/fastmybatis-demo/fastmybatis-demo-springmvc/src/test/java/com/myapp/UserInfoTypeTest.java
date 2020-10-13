package com.myapp;

import com.myapp.entity.UserInfo;
import com.myapp.entity.UserInfoType;
import com.myapp.mapper.UserInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author tanghc
 */
public class UserInfoTypeTest extends TestBase {
    @Autowired
    UserInfoMapper userInfoDao;

    @Test
    public void testGet() {
        UserInfo userInfo = userInfoDao.getById(3);
        print("枚举字段status：" + userInfo.getStatus().getCode());
        print(userInfo);
    }

    @Test
    public void testUpdate() {
        UserInfo userInfo = userInfoDao.getById(10);
        userInfo.setAddress("bbb");
        // 修改枚举值
        userInfo.setStatus(UserInfoType.INVALID);
        userInfoDao.updateIgnoreNull(userInfo);
    }

    @Test
    public void testSave() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(3);
        userInfo.setCity("杭州");
        userInfo.setAddress("aa");
        userInfo.setCreateTime(new Date());
        // 枚举值
        userInfo.setStatus(UserInfoType.VALID);
        userInfoDao.saveIgnoreNull(userInfo);
    }
}
