package cn.usbtg.session.impl;

import cn.usbtg.core.exception.BTGRuntimeException;
import cn.usbtg.session.BTGSession;
import cn.usbtg.session.BTGSessionContext;
import cn.usbtg.session.BTGSessionDao;
import cn.usbtg.session.BTGSessionIdGenerator;
import cn.usbtg.sutil.EmptyUtil;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义session管理器
 */
public class BTGStandardSessionContext implements BTGSessionContext {
    private String sessionIdKey = "BTGSESSIONID"; //session id key，默认为BTGSESSIONID
    private int sessionTimeoutMillis = 30 * 60 * 1000; //session过期时间(秒)，默认值为30分钟

    private int maxClearTimeoutMillis = 30 * 60 * 1000;//过期session清理机制触发周期(毫秒)
    private int maxUpdateLastActiveTimeMillis = 30 * 1000;//session最后访问时间同步更新机制触发周期(毫秒)，小于0时不进行更新，等于0时为实时更新，大于0时间隔更新
    private long lastClearTimeoutMillis = System.currentTimeMillis(); //上次session清理机制触发时间戳

    private static BTGStandardSessionContext sessionContext = new BTGStandardSessionContext();//session上下文
    private BTGSessionDao sessionDao = new BTGLocalSessionDao(); //session存储器，默认使用本地session管理
    private BTGSessionIdGenerator sessionIdGenerator = new BTGUUIDSessionIdGenerator(); //session id 生成器，默认使用uuid生成策略

    private Map<String, BTGSession> sessionCache = new ConcurrentHashMap<>(); //二级缓存
    private boolean useCache = true; //是否使用二级缓存(默认不启用)

    protected BTGStandardSessionContext() {

    }

    public static BTGStandardSessionContext getSessionContext() {
        return sessionContext;
    }

    public static BTGStandardSessionContext getSessionContext(BTGSessionDao sessionDao) {
        sessionContext.setSessionDao(sessionDao);
        return sessionContext;
    }

    @Override
    public void enableCache() {
        this.useCache = true;
    }

    @Override
    public void disableCache() {
        this.useCache = false;
        sessionCache.clear();
    }

    @Override
    public void addSession(BTGSession session) {
        if (EmptyUtil.isEmpty(session)) {
            throw new BTGRuntimeException("session is null");
        }
        session.active();
        this.sessionDao.saveSession(session);
        if (useCache) {
            this.sessionCache.put(session.getId(), session);
        }
        this.checkSessionTimeout();
    }

    @Override
    public void removeSession(String sessionId) {
        if (EmptyUtil.isEmpty(sessionId)) {
            throw new BTGRuntimeException("session id is null");
        }
        this.sessionDao.deleteSession(sessionId);
        if (useCache) {
            sessionCache.remove(sessionId);
        }
        this.checkSessionTimeout();
    }

    @Override
    public void refreshSession(BTGSession session) {
        if (EmptyUtil.isEmpty(session)) {
            throw new BTGRuntimeException("session is null");
        }

        //克隆一个要保存的session
        BTGSession resultSession = session.cloneMe();

        //若原始session存在，在保留原始值的前提下，使用新值覆盖旧值
        BTGSession oldSession = getSession(session.getId());
        if (EmptyUtil.isNotEmpty(oldSession)) {
            //填充原始session值
            Enumeration<String> enumerationAttrNamesOld = oldSession.getAttributeNames();
            while (enumerationAttrNamesOld.hasMoreElements()) {
                String attrName = enumerationAttrNamesOld.nextElement();
                Object value = oldSession.getAttribute(attrName);
                resultSession.addAttributeNotRefresh(attrName, value);
            }

            //填充要保存sessoin的值
            Enumeration<String> enumerationAttrNames = session.getAttributeNames();
            while (enumerationAttrNames.hasMoreElements()) {
                String attrName = enumerationAttrNames.nextElement();
                Object value = oldSession.getAttribute(attrName);
                resultSession.addAttributeNotRefresh(attrName, value);
            }
        }

        resultSession.active();
        this.sessionDao.refreshSession(resultSession);
        if (useCache) {
            sessionCache.put(resultSession.getId(), resultSession);
        }
        this.checkSessionTimeout();
    }

    @Override
    public BTGSession getSession(String sessionId) {
        if (EmptyUtil.isEmpty(sessionId)) return null;
        BTGSession session = sessionCache.get(sessionId);
        if (EmptyUtil.isEmpty(session)) {
            session = this.sessionDao.getSession(sessionId);
            if (useCache && EmptyUtil.isNotEmpty(session)) {
                sessionCache.put(session.getId(), session);
            }
        } else {
            this.active(session);
            this.sessionDao.active(session);
        }

        if (EmptyUtil.isEmpty(session)) {
            return null;
        }

        if (session.isInvalidate()) {
            this.sessionDao.deleteSession(sessionId);
            if (useCache) {
                this.sessionCache.remove(sessionId);
            }
            return null;
        }

        this.checkSessionTimeout();

        return session;
    }

    @Override
    public BTGSession getNewSession() {
        BTGSession session = new BTGStandardSession(this.sessionIdGenerator.genaeratorSessionId(), this.sessionTimeoutMillis);
        this.addSession(session);
        this.checkSessionTimeout();
        return session;
    }

    @Override
    public void active(BTGSession session) {
        if (EmptyUtil.isEmpty(session)) {
            throw new BTGRuntimeException("session is null");
        }

        //如果最大更新时间设置为小于0，则不进行更新
        if (this.maxUpdateLastActiveTimeMillis < 0) {
            return;
        }

        //如果最大更新时间加间隔周期时间大于当前时间，说明未到更新周期，不进行更新
        if (this.maxUpdateLastActiveTimeMillis > 0 && this.maxUpdateLastActiveTimeMillis + session.getLastUpdateMillis() > System.currentTimeMillis()) {
            return;
        }

        //当最大更新时间大于0并且最大更新时间加间隔周期时间小于当前时间，
        //或者最大更新时间等于0时，进行同步更新
        session.setLastUpdateMillis(System.currentTimeMillis());
        this.sessionDao.active(session);
        if (useCache) {
            this.sessionCache.get(session.getId()).active();
        }
        this.checkSessionTimeout();
    }

    @Override
    public Hashtable<String, BTGSession> getSessions() {
        return this.sessionDao.getSessions();
    }

    @Override
    public void clearTimeout() {
        this.lastClearTimeoutMillis = System.currentTimeMillis();
        this.sessionDao.clearTimeout();

        if (useCache) {
            Iterator<BTGSession> iterator = this.sessionCache.values().iterator();
            while (iterator.hasNext()) {
                BTGSession session = iterator.next();
                if (session.isInvalidate()) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * session清理算法机制：
     * 1、设置清理周期，默认为30分钟；
     * 2、非绝对清理，当触发了addSession、removeSession、refreshSession、getSession、getNewSession、active时触发清理机制；
     * 3、通过调用clearTimeout手动清理；
     */
    public void checkSessionTimeout() {
        if (this.lastClearTimeoutMillis + this.maxClearTimeoutMillis < System.currentTimeMillis()) {
            this.clearTimeout();
        }
    }

    @Override
    public void clear() {
        this.sessionDao.clear();
    }

    @Override
    public String getSessionIdKey() {
        return sessionIdKey;
    }

    @Override
    public void setSessionIdKey(String sesssionIdKey) {
        this.sessionIdKey = sesssionIdKey;
    }

    @Override
    public int getSessionTimeoutMillis() {
        return sessionTimeoutMillis;
    }

    @Override
    public void setSessionTimeoutMillis(int sessionTimeoutMillis) {
        this.sessionTimeoutMillis = sessionTimeoutMillis;
    }

    public int getMaxClearTimeoutMillis() {
        return maxClearTimeoutMillis;
    }

    public void setMaxClearTimeoutMillis(int maxClearTimeoutMillis) {
        this.maxClearTimeoutMillis = maxClearTimeoutMillis;
    }

    public int getMaxUpdateLastActiveTimeMillis() {
        return maxUpdateLastActiveTimeMillis;
    }

    public void setMaxUpdateLastActiveTimeMillis(int maxUpdateLastActiveTimeMillis) {
        this.maxUpdateLastActiveTimeMillis = maxUpdateLastActiveTimeMillis;
    }

    @Override
    public BTGSessionIdGenerator getSessionIdGenerator() {
        return sessionIdGenerator;
    }

    @Override
    public void setSessionIdGenerator(BTGSessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    @Override
    public BTGSessionDao getSessionDao() {
        return sessionDao;
    }

    @Override
    public void setSessionDao(BTGSessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public boolean isUseCache() {
        return useCache;
    }

    @Override
    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }
}