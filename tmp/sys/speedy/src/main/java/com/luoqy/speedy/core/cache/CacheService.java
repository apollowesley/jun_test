package com.luoqy.speedy.core.cache;

import java.util.Map;
import java.util.Set;

public interface CacheService {
	 /**
     * 存入缓存
     * @param key
     * @param cache
     */
    void putCache(String key, Cache cache);

    /**
     * 存入缓存
     * @param key 值名
     * @param cache 数据
     * @param timeOut 时长
     */
    void putCache(String key, Object datas, long timeOut);

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    Cache getCacheByKey(String key);

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    Object getCacheDataByKey(String key);

    /**
     * 获取所有缓存
     * @param key
     * @return
     */
    Map<String, Cache> getCacheAll();

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    boolean isContains(String key);

    /**
     * 清除所有缓存
     */
    void clearAll();

    /**
     * 清除对应缓存
     * @param key
     */
    void clearByKey(String key);

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    boolean isTimeOut(String key);

    /**
     * 获取所有key
     * @return
     */
    Set<String> getAllKeys();
}
