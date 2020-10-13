package cn.usbtg.session.impl;

import cn.usbtg.session.BTGSession;
import cn.usbtg.session.BTGSessionDao;
import cn.usbtg.session.BTGSessionIdGenerator;
import cn.usbtg.session.BTGSessionManager;
import cn.usbtg.session.util.EmptyUtil;

/**
 * 自定义session管理器
 */
public class BTGStandardSessionManager implements BTGSessionManager {
    private String sessionIdKey = "BTGSESSIONID"; //session id key，默认为BTGSESSIONID
    private int sessionTimeout = 30 * 60 * 1000; //session过期时间，默认值为30分钟
    private BTGSessionIdGenerator sessionIdGenerator; //session id 生成器，默认使用uuid生成策略
    private BTGSessionDao sessionDao; //session存储器，默认使用本地session管理

    public BTGStandardSessionManager() {
        this.sessionIdGenerator = new BTGUUIDSessionIdGenerator();
        this.sessionDao = new BTGLocalSessionDao();
    }

    public BTGStandardSessionManager(BTGSessionDao sessionDao) {
        this.sessionIdGenerator = new BTGUUIDSessionIdGenerator();
        this.sessionDao = sessionDao;
    }

    public BTGStandardSessionManager(BTGSessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
        this.sessionDao = new BTGLocalSessionDao();
    }

    public BTGStandardSessionManager(BTGSessionIdGenerator sessionIdGenerator, BTGSessionDao sessionDao) {
        this.sessionIdGenerator = sessionIdGenerator;
        this.sessionDao = sessionDao;
    }

    public BTGStandardSessionManager(BTGSessionDao sessionDao, BTGSessionIdGenerator sessionIdGenerator) {
        this(sessionIdGenerator, sessionDao);
    }

    @Override
    public BTGSession getSession(String sessionId) {
        if (EmptyUtil.isEmpty(sessionId)) return null;
        BTGSession session = this.sessionDao.getSession(sessionId);
        if (EmptyUtil.isEmpty(session)) return null;
        if (session.isInvalidate()) {
            this.sessionDao.deleteSession(sessionId);
            return null;
        }
        session.setLastAccessedTime(System.currentTimeMillis());
        this.sessionDao.refreshSession(session);
        return session;
    }

    @Override
    public BTGSession getNewSession() {
        return new BTGStandardSession(this.sessionIdGenerator.genaeratorSessionId(), this.sessionTimeout);
    }

    @Override
    public void addSession(BTGSession session) {
        if (EmptyUtil.isEmpty(session)) return;
        this.sessionDao.saveSession(session);
    }

    @Override
    public void removeSession(String sessionId) {
        if (EmptyUtil.isEmpty(sessionId)) return;
        this.sessionDao.deleteSession(sessionId);
    }

    @Override
    public void refreshSession(BTGSession session) {
        this.sessionDao.refreshSession(session);
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
    public int getSessionTimeout() {
        return sessionTimeout;
    }

    @Override
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
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
}