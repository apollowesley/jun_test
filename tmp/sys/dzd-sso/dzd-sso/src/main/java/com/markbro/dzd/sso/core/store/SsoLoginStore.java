package com.markbro.dzd.sso.core.store;


import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.user.SsoUser;
import com.markbro.dzd.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * local login store
 * 默认采用redis存储session
 * @author xuxueli 2018-04-02 20:03:11
 */

public class SsoLoginStore implements ISsoLoginStore, DisposableBean, InitializingBean {

    private String redisAddress;


    private int redisExpireMinite=30;

    public SsoLoginStore(String redisAddress) {
        this.redisAddress = redisAddress;
        this.afterPropertiesSet();
    }

    public SsoLoginStore(String redisAddress, int redisExpireMinite) {
        this.redisAddress = redisAddress;
        this.redisExpireMinite = redisExpireMinite;
        this.afterPropertiesSet();
    }

    @Override
    public int getExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    @Override
    public  SsoUser get(String storeKey) {

        String redisKey = redisKey(storeKey);
        Object objectValue = JedisUtil.getObjectValue(redisKey,SsoUser.class);
        if (objectValue != null) {
            SsoUser ssoUser = (SsoUser) objectValue;
            return ssoUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    @Override
    public  void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param ssoUser
     */
    @Override
    public void put(String storeKey, SsoUser ssoUser) {
        String redisKey = redisKey(storeKey);
        JedisUtil.setObjectValue(redisKey, ssoUser, redisExpireMinite * 60);  // minite to second
    }

    @Override
    public void putCache(String key, Object v) {
        JedisUtil.setObjectValue(key, v, redisExpireMinite * 60);
    }

    @Override
    public Object getCache(String key) {
        return JedisUtil.getObjectValue(key,Object.class);
    }

    private static String redisKey(String key){
        return SsoConf.SSO_SESSIONID.concat("#").concat(key);
    }

    @Override
    public void destroy() throws Exception {
        JedisUtil.close();
    }

    @Override
    public void afterPropertiesSet() {
        if(redisExpireMinite==0){
            redisExpireMinite=1440;
        }
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        JedisUtil.init(redisAddress);
    }
}
