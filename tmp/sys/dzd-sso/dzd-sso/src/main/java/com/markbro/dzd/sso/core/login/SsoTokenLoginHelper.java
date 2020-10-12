package com.markbro.dzd.sso.core.login;

import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.store.ISsoLoginStore;
import com.markbro.dzd.sso.core.store.ISsoSessionIdHelper;
import com.markbro.dzd.sso.core.user.SsoUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuxueli 2018-11-15 15:54:40
 */
public class SsoTokenLoginHelper implements ISsoLoginHelper {

    @Autowired
    ISsoSessionIdHelper ssoSessionIdHelper;
    @Autowired
    ISsoLoginStore ssoLoginStore;

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, String sessionId, SsoUser ssoUser, boolean ifRemember) {
        String storeKey = ssoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("login Fail, sessionId:" + sessionId);
        }
        ssoLoginStore.put(storeKey, ssoUser);
    }
    /**
     * client logout
     *
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String headerSessionId = request.getHeader(SsoConf.SSO_SESSIONID);
        request.getSession().removeAttribute(SsoConf.SSO_USER);
        String storeKey = ssoSessionIdHelper.parseStoreKey(headerSessionId);
        if (storeKey == null) {
            return;
        }
        ssoLoginStore.remove(storeKey);
    }

    @Override
    public SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response) {
        String headerSessionId = request.getHeader(SsoConf.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }

    @Override
    public boolean invalidSession(String sessionId) {
       return false;
    }

    @Override
    public boolean isInvalidSession(String sessionId) {
        return false;
    }

    @Override
    public String getSessionId(HttpServletRequest request) {
        String cookieSessionId = request.getHeader(SsoConf.SSO_SESSIONID);
        return cookieSessionId;
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
}
