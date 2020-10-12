package com.markbro.dzd.sso.core.store;

import com.markbro.dzd.sso.core.user.SsoUser;

/**
 * Created by Administrator on 2018/12/2.
 */
public interface ISsoLoginStore {

    int getExpireMinite();
    SsoUser get(String storeKey);
    void remove(String storeKey);
    void put(String storeKey, SsoUser ssoUser);

    void putCache(String key, Object v);
    Object getCache(String key);
}
