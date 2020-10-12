package com.jfast.web.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/5/12 16:03
 */
@Slf4j
public class CacheKit {

    private static CacheManager cacheManager;

    private static final String DEFAULT_CACHE = "default_cache";

    public static void init(CacheManager cacheManager) {
        CacheKit.cacheManager = cacheManager;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    static Cache getOrAddCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            synchronized (CacheKit.class) {
                cache = cacheManager.getCache(cacheName);
                if (cache == null) {
                    log.warn("Could not find cache config [" + cacheName + "], using default.");
                    cacheManager.addCacheIfAbsent(cacheName);
                    cache = cacheManager.getCache(cacheName);
                    log.debug("Cache [" + cacheName + "] started.");
                }
            }
        }
        return cache;
    }

    public static void put(String cacheName, Object key, Object value) {
        getOrAddCache(cacheName).put(new Element(key, value));
    }

    public static void put(Object key, Object value) {
        put(DEFAULT_CACHE, key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String cacheName, Object key) {
        Element element = getOrAddCache(cacheName).get(key);
        return element != null ? (T)element.getObjectValue() : null;
    }

    public static <T> T get(Object key) {
       return get(DEFAULT_CACHE, key);
    }

    @SuppressWarnings("rawtypes")
    public static List getKeys(String cacheName) {
        return getOrAddCache(cacheName).getKeys();
    }

    public static void remove(String cacheName, Object key) {
        getOrAddCache(cacheName).remove(key);
    }

    public static void removeAll(String cacheName) {
        getOrAddCache(cacheName).removeAll();
    }

}
