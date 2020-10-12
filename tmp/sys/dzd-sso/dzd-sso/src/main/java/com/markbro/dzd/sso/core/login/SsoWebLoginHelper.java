package com.markbro.dzd.sso.core.login;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.store.ISsoLoginStore;
import com.markbro.dzd.sso.core.store.ISsoSessionIdHelper;
import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuxueli 2018-04-03
 */
public class SsoWebLoginHelper implements ISsoLoginHelper {

    @Autowired
    ISsoSessionIdHelper ssoSessionIdHelper;
    @Autowired
    ISsoLoginStore ssoLoginStore;
    /**
     * client login
     *
     * @param response
     * @param sessionId
     * @param ifRemember    true: cookie not expire, false: expire when browser close （server cookie）
     * @param ssoUser
     */
    @Override
    public  void login(HttpServletRequest request, HttpServletResponse response,
                       String sessionId,
                       SsoUser ssoUser,
                       boolean ifRemember) {
        if(ssoSessionIdHelper==null){
            throw new RuntimeException("login Fail, ssoSessionIdHelper is null!");
        }
        if(ssoLoginStore==null){
            throw new RuntimeException("login Fail, ssoLoginStore is null!");
        }

        String storeKey = ssoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("login Fail, sessionId:" + sessionId);
        }

        ssoLoginStore.put(storeKey, ssoUser);
        request.getSession().setAttribute(SsoConf.SSO_USER,ssoUser);
        CookieUtil.set(response, SsoConf.SSO_SESSIONID, sessionId, ifRemember);

    }

    /**
     * client logout
     *
     * @param request
     * @param response
     */
    @Override
    public   void logout(HttpServletRequest request,
                         HttpServletResponse response) {

        String cookieSessionId = CookieUtil.getValue(request, SsoConf.SSO_SESSIONID);
        request.getSession().removeAttribute(SsoConf.SSO_USER);
        CookieUtil.remove(request, response, SsoConf.SSO_SESSIONID);
        if (cookieSessionId==null) {
            return;
        }
        String storeKey = ssoSessionIdHelper.parseStoreKey(cookieSessionId);
        if (storeKey != null) {
            ssoLoginStore.remove(storeKey);
        }

    }



    /**
     * login check
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response){

        String cookieSessionId = CookieUtil.getValue(request, SsoConf.SSO_SESSIONID);

        //Cookie不存在SSO_SESSIONID
        if(StringUtils.isEmpty(cookieSessionId)){
            SsoUser ssoUser =(SsoUser)  request.getSession().getAttribute(SsoConf.SSO_USER);
            if(ssoUser!=null){
                //Cookie不存在,但session有用户，可能是人工手工删除了Cookie
                cookieSessionId=ssoSessionIdHelper.makeSessionId(ssoUser);//根据session的用户再把sesssId构造出来
                ssoUser = this.loginCheck(cookieSessionId);
                if (ssoUser != null) {
                    CookieUtil.set(response, SsoConf.SSO_SESSIONID, cookieSessionId, false);
                    request.getSession().setAttribute(SsoConf.SSO_USER,ssoUser);
                    return ssoUser;
                }
            }
            return null;
        }
        // cookie user
        SsoUser ssoUser = this.loginCheck(cookieSessionId);
        if (ssoUser != null) {
            request.getSession().setAttribute(SsoConf.SSO_USER,ssoUser);
            return ssoUser;
        }
        // get sessionId from param
        String paramSessionId = request.getParameter(SsoConf.SSO_SESSIONID);
        ssoUser = loginCheck(paramSessionId);
        if (ssoUser != null) {
            CookieUtil.set(response, SsoConf.SSO_SESSIONID, paramSessionId, false);    // expire when browser close （client cookie）
            request.getSession().setAttribute(SsoConf.SSO_USER,ssoUser);
            return ssoUser;
        }

        return null;
    }

    @Override
    public boolean invalidSession(String sessionId) {
        String storeKey = ssoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return false;
        }
        SsoUser ssoUser = ssoLoginStore.get(storeKey);
        if (ssoUser != null) {
            String version = ssoSessionIdHelper.parseVersion(sessionId);
            if (ssoUser.getVersion().equals(version)) {
                ssoLoginStore.remove(storeKey);
                ssoLoginStore.putCache(SsoConf.KEY_INVALID_SESSION + "_" + sessionId, "1");//记录被踢出的sessionId
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean isInvalidSession(String sessionId) {
        if(StringUtils.isEmpty(sessionId)) {
            return false;
        }
        Object v=ssoLoginStore.getCache(SsoConf.KEY_INVALID_SESSION + "_" + sessionId);
        if(v==null) {
            return false;
        }
        if("1".equals(v.toString())){
            return true;
        }
        return false;
    }
    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public SsoUser loginCheck(String  sessionId){

        String storeKey = ssoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }

        SsoUser ssoUser = ssoLoginStore.get(storeKey);
        if (ssoUser != null) {
            String version = ssoSessionIdHelper.parseVersion(sessionId);
            if (ssoUser.getVersion().equals(version)) {

                // After the expiration time has passed half, Auto refresh
                if ((System.currentTimeMillis() - ssoUser.getExpireFreshTime()) > ssoUser.getExpireMinite()/2*60*1000) {
                    ssoUser.setExpireFreshTime(System.currentTimeMillis());
                    ssoLoginStore.put(storeKey, ssoUser);
                }

                return ssoUser;
            }
        }
        return null;
    }


    /**
     * get sessionid by cookie
     *
     * @param request
     * @return
     */
    @Override
    public  String getSessionId(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, SsoConf.SSO_SESSIONID);
        return cookieSessionId;
    }


}
