package com.flypigs.lock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JedisClusterLock implements DistributedLock {
    private static final String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " + "then "
            + "    return redis.call(\"del\",KEYS[1]) " + "else " + "    return 0 " + "end ";
    private JedisCluster jedisCluster;
    private String lockKey;
    private String lockValue = UUID.randomUUID().toString();
    private boolean locked = false;
    private boolean unLocked = false;

    public JedisClusterLock(JedisCluster jedisCluster) {
        Assert.notNull(jedisCluster,"jedisCluster不能为空");
        this.jedisCluster = jedisCluster;
    }

    public boolean tryLock(final String lock,Integer expireSeconds){
        Assert.notNull(StringUtils.isNotBlank(lock),"lock不能为空");
        lockKey = lock;
        try {
            final Integer expireSecondsTemp = expireSeconds == null || expireSeconds <= 0 ? 5 : expireSeconds;
            String rs = jedisCluster.set(lock, lockValue, "NX", "EX", expireSeconds);
            locked = "ok".equalsIgnoreCase(rs);
        }catch (Exception e){
            e.printStackTrace();
            locked = false;
        }
        return locked;
    }

    public boolean unlock() {
        if (!locked) {
            return false;
        }
        try {
            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();
            keys.add(lockKey);
            values.add(lockValue);
            unLocked = ((Long) jedisCluster.eval(UNLOCK_LUA, keys, values) == 1);
        }catch (Exception e){
            e.printStackTrace();
            unLocked = false;
        }
        return unLocked;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isUnLocked() {
        return unLocked;
    }
}
