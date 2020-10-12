package com.markbro.dzd.sso.server.service;

import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.user.SsoUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class DemoSsoUserServiceImpl implements SsoUserService{
    private  final Logger log= LoggerFactory.getLogger(getClass());
    private static List<SsoUserInfo> mockUserList = new ArrayList<SsoUserInfo>();
    static {
        for (int i = 0; i <5; i++) {
            SsoUserInfo userInfo = new SsoUserInfo();
            userInfo.setUserid(1000+i+"");
            userInfo.setUsername("user" + (i>0?String.valueOf(i):""));
            userInfo.setPassword("123456");
            mockUserList.add(userInfo);
        }
    }

    @Override
    public ReturnT<SsoUserInfo> findUser(String username, String password) {

        if (username==null || username.trim().length()==0) {
            return new ReturnT<SsoUserInfo>(ReturnT.FAIL_CODE, "Please input username.");
        }
        if (password==null || password.trim().length()==0) {
            return new ReturnT<SsoUserInfo>(ReturnT.FAIL_CODE, "Please input password.");
        }

        // mock user
        for (SsoUserInfo mockUser: mockUserList) {
            if (mockUser.getUsername().equals(username) && mockUser.getPassword().equals(password)) {
                return new ReturnT<SsoUserInfo>(mockUser);
            }
        }

        return new ReturnT<SsoUserInfo>(ReturnT.FAIL_CODE, "username or password is invalid.");
    }

    @Override
    public void afterValidateSuccess(SsoUserInfo ssoUserInfo) {
        log.info(">>>>>>>>>>>>>>>>> 用户["+ssoUserInfo.getUsername()+"] sso login success!");
    }

}
