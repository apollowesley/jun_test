package com.markbro.dzd.sso.core.login;

import com.markbro.dzd.sso.core.user.SsoUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/12/2.
 */
public interface ISsoLoginHelper {

    void login(HttpServletRequest request, HttpServletResponse response, String sessionId, SsoUser ssoUser, boolean ifRemember);
    void logout(HttpServletRequest request, HttpServletResponse response);
    SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response);
    boolean invalidSession(String sessionId);
    boolean isInvalidSession(String sessionId);//判断sessionId是否失效
    String getSessionId(HttpServletRequest request);
}
