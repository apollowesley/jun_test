package com.markbro.dzd.sso.core.store;

import com.markbro.dzd.sso.core.user.SsoUser;

/**
 * Created by Administrator on 2018/12/2.
 */
public interface ISsoSessionIdHelper {
    public String makeSessionId(SsoUser ssoUser);
    public String parseStoreKey(String sessionId);
    public String parseVersion(String sessionId);
}
