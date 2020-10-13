package cn.usbtg.session.impl;

import cn.usbtg.session.BTGSession;
import cn.usbtg.session.BTGSessionDao;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.RedisPlugin;

/**
 * 基于redis的session存储实现
 */
public class BTGRedisSessionDao implements BTGSessionDao {

    //redis session cache
    private Cache sessionCache;

    public BTGRedisSessionDao(String cacheName, String host) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, int port) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, port);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, int port, int timeout) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, port, timeout);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, int port, int timeout, String password) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, port, timeout, password);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, int port, int timeout, String password, int database) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, port, timeout, password, database);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, int port, int timeout, String password, int database, String clientName) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, port, timeout, password, database, clientName);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, int port, String password) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, port, password);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    public BTGRedisSessionDao(String cacheName, String host, String password) {
        RedisPlugin redisPluginSession = new RedisPlugin(cacheName, host, password);
        redisPluginSession.start();
        this.sessionCache = Redis.use(cacheName);
    }

    @Override
    public void saveSession(BTGSession session) {
        this.sessionCache.setex(session.getId(), Integer.parseInt(PropKit.get("session.maxAge")), session);
    }

    @Override
    public void deleteSession(String sessionId) {
        this.sessionCache.del(sessionId);
    }

    @Override
    public BTGSession getSession(String sessionId) {
        return this.sessionCache.get(sessionId);
    }

    @Override
    public void refreshSession(BTGSession session) {
        this.deleteSession(session.getId());
        this.saveSession(session);
    }

}
