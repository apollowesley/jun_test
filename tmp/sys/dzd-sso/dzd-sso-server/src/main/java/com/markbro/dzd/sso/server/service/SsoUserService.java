package com.markbro.dzd.sso.server.service;


import com.markbro.dzd.sso.core.entity.ReturnT;
import com.markbro.dzd.sso.core.user.SsoUserInfo;

/**
 * 实现sso的系统用户登录要该接口根据用户名密码在系统中查找用户来实现登录
 */
public interface SsoUserService {

    public ReturnT<SsoUserInfo> findUser(String username, String password);

    public void afterValidateSuccess(SsoUserInfo ssoUserInfo);
}
