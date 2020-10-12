package com.markbro.dzd.sso.core.store;


import com.markbro.dzd.sso.core.user.SsoUser;

/**
 * make client sessionId
 *
 *      client: cookie = [userid#version]
 *      server: redis
 *                  key = [userid]
 *                  value = user (user.version, valid this)
 *
 * //   group         The same group shares the login status, Different groups will not interact
 *
 * @author xuxueli 2018-11-15 15:45:08
 */
public class SsoSessionIdHelper implements ISsoSessionIdHelper {


    /**
     * make client sessionId
     *
     * @param ssoUser
     * @return
     */
    @Override
    public  String makeSessionId(SsoUser ssoUser){
        String sessionId = ssoUser.getUserid().concat("_").concat(ssoUser.getVersion());
        return sessionId;
    }

    /**
     * parse storeKey from sessionId
     *
     * @param sessionId
     * @return
     */
    @Override
    public  String parseStoreKey(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[0]!=null
                    && sessionIdArr[0].trim().length()>0) {
                String userId = sessionIdArr[0].trim();
                return userId;
            }
        }
        return null;
    }

    /**
     * parse version from sessionId
     *
     * @param sessionId
     * @return
     */
    @Override
    public  String parseVersion(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[1]!=null
                    && sessionIdArr[1].trim().length()>0) {
                String version = sessionIdArr[1].trim();
                return version;
            }
        }
        return null;
    }

}
