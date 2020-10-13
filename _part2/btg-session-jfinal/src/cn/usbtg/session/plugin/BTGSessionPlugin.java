package cn.usbtg.session.plugin;

import cn.usbtg.session.BTGSessionDao;
import cn.usbtg.session.BTGSessionIdGenerator;
import cn.usbtg.session.kit.SessionKit;
import com.jfinal.plugin.IPlugin;

/**
 * BTG session jfinal插件
 */
public class BTGSessionPlugin implements IPlugin {
    private String sessionIdKey;
    private int sessionTimeoutMillis;
    private int maxClearTimeoutMillis;
    private BTGSessionDao sessionDao;
    private BTGSessionIdGenerator sessionIdGenerator;
    private boolean isUseCache;

    public BTGSessionPlugin() {

    }

    public BTGSessionPlugin(BTGSessionDao sessionDao) {
        SessionKit.setSessionDao(sessionDao);
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    public String getSessionIdKey() {
        return sessionIdKey;
    }

    public void setSessionIdKey(String sessionIdKey) {
        this.sessionIdKey = sessionIdKey;
        SessionKit.getSessionContext().setSessionIdKey(sessionIdKey);
    }

    public int getSessionTimeoutMillis() {
        return sessionTimeoutMillis;
    }

    public void setSessionTimeoutMillis(int sessionTimeoutMillis) {
        this.sessionTimeoutMillis = sessionTimeoutMillis;
        SessionKit.getSessionContext().setSessionTimeoutMillis(sessionTimeoutMillis);
    }

    public int getMaxClearTimeoutMillis() {
        return maxClearTimeoutMillis;
    }

    public void setMaxClearTimeoutMillis(int maxClearTimeoutMillis) {
        this.maxClearTimeoutMillis = maxClearTimeoutMillis;
        SessionKit.getSessionContext().setMaxClearTimeoutMillis(maxClearTimeoutMillis);
    }

    public BTGSessionDao getSessionDao() {
        return sessionDao;
    }

    public void setSessionDao(BTGSessionDao sessionDao) {
        this.sessionDao = sessionDao;
        SessionKit.getSessionContext().setSessionDao(sessionDao);
    }

    public BTGSessionIdGenerator getSessionIdGenerator() {
        return sessionIdGenerator;
    }

    public void setSessionIdGenerator(BTGSessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
        SessionKit.getSessionContext().setSessionIdGenerator(sessionIdGenerator);
    }

    public boolean isUseCache() {
        return isUseCache;
    }

    public void setUseCache(boolean isUseCache) {
        this.isUseCache = isUseCache;
        SessionKit.getSessionContext().setUseCache(isUseCache);
    }
}