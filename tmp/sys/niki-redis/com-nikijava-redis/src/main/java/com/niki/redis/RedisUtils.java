package com.niki.redis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedisPool;

import java.io.Serializable;

/**
 * @ProjectName: trade
 * @Package: com.utils
 * @ClassName: TradePayRedisUtils
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/4/9 13:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Component
@Slf4j
public class RedisUtils extends CacheManager {

    public String commonType = "project_name";

    public RedisUtils(ShardedJedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    public Long getTTL(String key) {
        key = commonType + key;
        return super.getTTL(key);
    }

    @Override
    public Boolean exists(String key) {
        key = commonType + key;
        return super.exists(key);
    }

    @Override
    public Boolean remove(String key) {
        key = commonType + key;
        return super.remove(key);
    }

    @Override
    public Boolean saveString(String key, String dataString, int seconds) {
        key = commonType + key;
        return super.saveString(key, dataString, seconds);
    }

    @Override
    public String getString(String key) {
        key = commonType + key;
        return super.getString(key);
    }

    @Override
    public <T extends Serializable> void saveObject(String key, T data) {
        key = commonType + key;
        super.saveObject(key, data);
    }

    @Override
    public <T> T getObject(String key) {
        key = commonType + key;
        return super.getObject(key);
    }

    @Override
    public Boolean removeObject(String key) {
        key = commonType + key;
        return super.removeObject(key);
    }

    @Override
    public Boolean tryLock(String key, int seconds) {
        key = commonType + key;
        return super.tryLock(key, seconds);
    }

    @Override
    public Boolean unLock(String key) {
        key = commonType + key;
        return super.unLock(key);
    }

    @Override
    public <T> T rPopObject(String key) {
        key = commonType + key;
        return super.rPopObject(key);
    }

    @Override
    public void lPushObj(String key, Object data) {
        key = commonType + key;
        super.lPushObj(key, data);
    }

    @Override
    public Boolean hexists(String key, String field) {
        key = commonType + key;
        return super.hexists(key, field);
    }

    @Override
    public Long incr(String key, int seconds) {
        key = commonType + key;
        return super.incr(key, seconds);
    }

    @Override
    public <T extends Serializable> void saveHashMap(String cacheName, String field, T data, int seconds) {
        cacheName = commonType + cacheName;
        super.saveHashMap(cacheName, field, data, seconds);
    }

    @Override
    public <T> T getHashMap(String cacheName, String field) {
        cacheName = commonType + cacheName;
        return super.getHashMap(cacheName, field);
    }

    @Override
    public Boolean removeHashMap(String cacheName, String field) {
        cacheName = commonType + cacheName;
        return super.removeHashMap(cacheName, field);
    }


}