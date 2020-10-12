package com.markbro.dzd.sso.core.store;


import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.util.DesUtil;

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
public class MySsoSessionIdHelper implements ISsoSessionIdHelper {

    private static final String token_key="token_ke";
    /**
     * make client sessionId
     *
     * @param ssoUser
     * @return
     */
    @Override
    public  String makeSessionId(SsoUser ssoUser){
        String sessionId = ssoUser.getUserid().concat("#").concat(ssoUser.getVersion());
        try {
           return DesUtil.encrypt(sessionId, token_key);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            sessionId= DesUtil.decrypt(sessionId, token_key);
        } catch (Exception e) {}
        if (sessionId!=null && sessionId.indexOf("#")>-1) {
            String[] sessionIdArr = sessionId.split("#");
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
        try {
            sessionId= DesUtil.decrypt(sessionId, token_key);
        } catch (Exception e) {}

        if (sessionId!=null && sessionId.indexOf("#")>-1) {
            String[] sessionIdArr = sessionId.split("#");
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
