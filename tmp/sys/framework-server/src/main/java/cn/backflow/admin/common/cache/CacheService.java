package cn.backflow.admin.common.cache;

import cn.backflow.lib.util.SpringContextUtil;
import org.springframework.cache.CacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis cache service
 * Created by Nandy on 2016/5/9.
 */
public class CacheService {

    private static CacheManager cacheManager = SpringContextUtil.getBean(CacheManager.class);

    public static Map<String, Integer> get(String loginFailureMap) {
        return new HashMap<>();
    }

    public static void put(String loginFailureMap, Map<String, Integer> loginFailMap) {

    }

    public static void set(String key, Object value) {
    }

}
