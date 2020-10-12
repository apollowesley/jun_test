package com.flypigs.lock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RedisTemplateLock implements DistributedLock {
    private static final String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " + "then "
            + "    return redis.call(\"del\",KEYS[1]) " + "else " + "    return 0 " + "end ";
    private RedisTemplate redisTemplate;
    private String lockKey;
    private String lockValue = UUID.randomUUID().toString();
    private boolean locked = false;
    private boolean unLocked = false;

    public RedisTemplateLock(RedisTemplate redisTemplate) {
        Assert.notNull(redisTemplate,"redisTemplate不能为空");
        this.redisTemplate = redisTemplate;
    }

    public boolean tryLock(final String lock,Integer expireSeconds){
        Assert.notNull(StringUtils.isNotBlank(lock),"lock不能为空");
        lockKey = lock;
        try {
            final Integer expireSecondsTemp = expireSeconds == null || expireSeconds <= 0 ? 5 : expireSeconds;
            String rs = (String) redisTemplate.execute(new RedisCallback<String>(){
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    JedisCommands commands = (JedisCommands) redisConnection.getNativeConnection();
                    return commands.set(lockKey, lockValue, "NX", "EX", expireSecondsTemp);
                }
            });
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
            unLocked = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>(){
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    JedisCommands connection = (JedisCommands) redisConnection.getNativeConnection();
                    List<String> keys = new ArrayList<>();
                    List<String> values = new ArrayList<>();
                    keys.add(lockKey);
                    values.add(lockValue);
                    // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                    // 集群模式
                    if (connection instanceof JedisCluster) {
                        return (Long) ((JedisCluster) connection).eval(UNLOCK_LUA, keys, values) == 1;
                    }
                    // 单机模式
                    else if (connection instanceof Jedis) {
                        return (Long) ((Jedis) connection).eval(UNLOCK_LUA, keys, values) == 1;
                    }
                    return false;
                }
            });
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
