package com.iotechn.iot.executor.compment;

import com.iotechn.iot.executor.dev.ICache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-28
 * Time: 下午5:39
 */
public class RedisCache4Groovy implements ICache {

    private StringRedisTemplate stringRedisTemplate;
    private Long executorId;

    public RedisCache4Groovy(Long executorId, StringRedisTemplate stringRedisTemplate){
        this.executorId = executorId;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(executorId + "_" + key,value);
    }

    @Override
    public void inc(String key, long delta) {
        stringRedisTemplate.opsForValue().increment(executorId + "_" + key,delta);
    }

    @Override
    public void inc(String key, double delta) {
        stringRedisTemplate.opsForValue().increment(executorId + "_" + key,delta);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
