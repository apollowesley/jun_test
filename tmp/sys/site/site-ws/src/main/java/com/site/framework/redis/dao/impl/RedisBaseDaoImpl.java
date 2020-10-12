package com.site.framework.redis.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.site.framework.redis.dao.RedisBaseDao;

/**
 * Redis基类Dao接口实现类
 * 
 */
public class RedisBaseDaoImpl implements RedisBaseDao {

    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;

    /**
     * 设置字符串
     * 
     * @param key
     *            key
     * @param value
     *            字符串值
     */
    @SuppressWarnings("unchecked")
    @Override
    public void set(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * 设置字符串(含过期时间)
     * 
     * @param key
     *            key
     * @param value
     *            字符串值
     * @param timeout
     *            过期时间，单位：秒
     */
    @SuppressWarnings("unchecked")
    @Override
    public void set(String key, String value, long timeout) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置多个字符串
     * 
     * @param paramMap
     *            字符串Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public void mset(Map<String, String> paramMap) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.multiSet(paramMap);
    }

    /**
     * 获取字符串
     * 
     * @param key
     *            key
     * @return String key对应的字符串值
     */
    @SuppressWarnings("unchecked")
    @Override
    public String get(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 根据key列表获取字符串列表
     * 
     * @param keys
     *            key列表
     * @return List<String> key列表对应的字符串列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> mget(List<String> keys) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.multiGet(keys);
    }

    /**
     * 设置对象(采用Redis的String存储)
     * 
     * @param key
     *            key
     * @param T
     *            对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void setObject(String key, T T) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, T);
    }

    /**
     * 设置对象(采用Redis的String存储，含过期时间)
     * 
     * @param key
     *            key
     * @param T
     *            对象
     * @param timeout
     *            过期时间，单位：秒
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void setObject(String key, T T, long timeout) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, T, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置多key的同一类型对象(采用Redis的String存储)
     * 
     * @param paramMap
     *            参数Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void msetObject(Map<String, T> paramMap) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        valueOperations.multiSet(paramMap);
    }

    /**
     * 根据key获取对象(对象采用Redis的String存储)
     * 
     * @param key
     *            key
     * @return T 对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T getObject(String key) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 根据key列表获取同一对象列表(列表中的对象采用Redis的String存储)
     * 
     * @param keys
     *            keys列表
     * @return List<T> 对象列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> mgetObject(List<String> keys) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.multiGet(keys);
    }

    /** ===============Redis-String数据结构接口END=============== */

    /** ===============Redis-HASH数据结构接口START=============== */
    /**
     * Hash设置,可用于保存对象或者保存对象的单个field
     * 
     * @param key
     *            Hash表的key
     * @param field
     *            Hash表中的域field
     * @param T
     *            对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void hset(String key, String field, T T) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, field, T);
    }

    /**
     * Hash批量设置,可用于保存多个对象或者保存单个对象的多个field
     * 
     * @param key
     *            Hash表的key
     * @param paramMap
     *            Hash表的field和Value组成的Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> void hmset(String key, Map<String, T> paramMap) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, paramMap);
    }

    /**
     * 根据Hash表的key和域Field获取对应的Value(可用于获取对象或者获取对象的单个field)
     * 
     * @param key
     *            Hash表的key
     * @param field
     *            Hash表中的域field
     * @return T Hash的key和域Field获取对应的Value
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T hget(String key, String field) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, field);
    }

    /**
     * 根据Hash表的key和域Field列表获取对应的Value列表(可用于获取多个对象或者对象的多个属性)
     * 
     * @author majun
     * @date 2014-9-28
     * @param key
     *            Hash表的key
     * @param fields
     *            域Field列表
     * @return List<T> Hash的key和域Field列表获取对应的Value列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> hmget(String key, List<String> fields) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.multiGet(key, fields);
    }

    /**
     * 根据Hash表的key获取对应的所有对象(可用于获取多个对象)
     * 
     * @param key
     *            Hash表的key
     * @return Map<String, T> Hash表的key对应的所有对象Map
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> hgetAll(String key) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * 根据Hash表的key和域Field进行删除
     * 
     * @param <T>
     * @param key
     *            Hash表的key
     * @param fields
     *            Hash表对应域Field数组
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> void hDel(String key, Object... fields) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, fields);
    }

    /** ===============Redis-HASH数据结构接口END=============== */

    /** ===============Redis接口START=============== */
    /**
     * 设置key在多少秒后过期
     * 
     * @param key
     *            key
     * @param timeout
     *            过期时间
     * @return boolean 是否设置成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置key在固定的某个时刻后过期
     * 
     * @param key
     *            key
     * @param date
     *            固定的某个时刻
     * @return boolean 是否设置成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 根据key删除单个对象
     * 
     * @param key
     *            Redis中存储的key
     */
    @SuppressWarnings("unchecked")
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 根据key列表删除多个对象
     * 
     * @param keys
     *            keys列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public void deleteAll(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /** ===============Redis接口END=============== */

    @SuppressWarnings("rawtypes")
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @SuppressWarnings("rawtypes")
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
