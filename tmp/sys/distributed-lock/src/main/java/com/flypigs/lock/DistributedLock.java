package com.flypigs.lock;

import redis.clients.jedis.JedisCluster;

/**
 * 分布锁接口
 */
public interface DistributedLock {

    boolean tryLock(String key,Integer expireSecond);

    boolean unlock();

    boolean isLocked();

    boolean isUnLocked();
}
