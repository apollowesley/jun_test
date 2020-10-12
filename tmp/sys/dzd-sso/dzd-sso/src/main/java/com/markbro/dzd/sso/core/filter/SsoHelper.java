package com.markbro.dzd.sso.core.filter;


import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.login.ISsoLoginHelper;
import com.markbro.dzd.sso.core.store.ISsoLoginStore;
import com.markbro.dzd.sso.core.store.ISsoSessionIdHelper;
import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.user.SsoUserInfo;
import com.markbro.dzd.sso.core.util.RequestContextHolderUtil;
import com.markbro.dzd.sso.core.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 该类除了login方法外，基本就是对ssoLoginHelper的再一层封装。在Controller中可以直接使用该类只需要调用一个方法。如果使用ssoLoginHelper则稍微有点麻烦。
 */
@Component
public class SsoHelper {

    protected static Logger log = LoggerFactory.getLogger(SsoHelper.class);

    static ISsoLoginStore ssoLoginStore = SpringContextHolder.getBean("ssoLoginStore");

    static ISsoSessionIdHelper ssoSessionIdHelper = SpringContextHolder.getBean("ssoSessionIdHelper");

    static ISsoLoginHelper ssoLoginHelper = SpringContextHolder.getBean("ssoLoginHelper");


    public static String login(SsoUserInfo userInfo,boolean ifRemember){

        if(!SsoConf.SSO_ENABLE){
            return "";
        }
        HttpServletRequest request= RequestContextHolderUtil.getRequest();
        HttpServletResponse response = RequestContextHolderUtil.getResponse();
        //sso 单点登录
        // 1、make xxl-sso user
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(userInfo.getUserid());
        ssoUser.setUsername(userInfo.getUsername());
        ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUser.setExpireMinite(ssoLoginStore.getExpireMinite());
        ssoUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、make session id
        String sessionId = ssoSessionIdHelper.makeSessionId(ssoUser);

        // 3、login, store storeKey + cookie sessionId
        ssoLoginHelper.login(request,response, sessionId, ssoUser, ifRemember);

        request.getSession().setAttribute(SsoConf.SSO_USER,ssoUser);
        return sessionId;
    }
    public static void logout(HttpServletRequest request,HttpServletResponse response){
        if(!SsoConf.SSO_ENABLE){
            return;
        }


        ssoLoginHelper.logout(request,response);
    }
    public static SsoUser loginChecked(HttpServletRequest request,HttpServletResponse response){
        if(!SsoConf.SSO_ENABLE){
            return null;
        }
        return ssoLoginHelper.loginCheck(request,response);
    }

    public static boolean invalidSession(String sessionId){
        if(!SsoConf.SSO_ENABLE){
            return false;
        }
        boolean flag = ssoLoginHelper.invalidSession(sessionId);
        return flag;
    }
}
