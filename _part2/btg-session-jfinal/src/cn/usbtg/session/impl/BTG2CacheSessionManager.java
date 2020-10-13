package cn.usbtg.session.impl;

import cn.usbtg.session.BTGSession;
import cn.usbtg.session.BTGSessionDao;
import cn.usbtg.session.BTGSessionIdGenerator;
import cn.usbtg.session.util.EmptyUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 添加了二级缓存的session管理器
 */
public class BTG2CacheSessionManager extends BTGStandardSessionManager {

    //本地二级缓存
    private Map<String, BTGSession> cacheSession = new ConcurrentHashMap<String, BTGSession>();

    public BTG2CacheSessionManager() {
        super();
    }

    public BTG2CacheSessionManager(BTGSessionDao sessionDao) {
        super(sessionDao);
    }

    public BTG2CacheSessionManager(BTGSessionIdGenerator sessionIdGenerator) {
        super(sessionIdGenerator);
    }

    public BTG2CacheSessionManager(BTGSessionIdGenerator sessionIdGenerator, BTGSessionDao sessionDao) {
        super(sessionIdGenerator, sessionDao);
    }

    public BTG2CacheSessionManager(BTGSessionDao sessionDao, BTGSessionIdGenerator sessionIdGenerator) {
        super(sessionDao, sessionIdGenerator);
    }

    @Override
    public void addSession(BTGSession session) {
        if (EmptyUtil.isEmpty(session)) return;
        cacheSession.put(session.getId(), session);
        super.addSession(session);
    }

    @Override
    public void removeSession(String sessionId) {
        if (EmptyUtil.isEmpty(sessionId)) return;
        cacheSession.remove(sessionId);
        super.removeSession(sessionId);
    }

    @Override
    public BTGSession getSession(String sessionId) {
        if (EmptyUtil.isEmpty(sessionId)) return null;
        BTGSession session = cacheSession.get(sessionId);
        if (EmptyUtil.isEmpty(session)) {
            session = super.getSession(sessionId);
            if (EmptyUtil.isNotEmpty(session)) {
                cacheSession.put(session.getId(), session);
            }
        } else {
            session.setLastAccessedTime(System.currentTimeMillis());
            super.refreshSession(session);
        }
        return session;
    }
}