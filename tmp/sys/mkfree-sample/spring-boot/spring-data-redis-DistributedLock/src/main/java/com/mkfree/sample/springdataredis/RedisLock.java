package com.mkfree.sample.springdataredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    private static final String LOCK_SUFFIX = "_lock";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分布式锁
     *
     * @param key
     * @return
     */
    public void lock(String key) {
        boolean lock;
        while (true) {
            lock = stringRedisTemplate.opsForValue().setIfAbsent(key + LOCK_SUFFIX, "");
            if (lock) {
                // 设置分布式锁最长时间为5秒，超时自动去除，防止死锁的情况发生
                stringRedisTemplate.expire(key + LOCK_SUFFIX, 5, TimeUnit.SECONDS);
                log.info("setting expire 5 seconds");
                break;
            }
        }
    }

    /**
     * 解除分布式锁
     *
     * @param key
     */
    public void unLock(String key) {
        stringRedisTemplate.delete(key + LOCK_SUFFIX);
    }
}
