package org.simplestudio.restful.httpclient.cache;

import java.util.HashMap;
import java.util.Map;

public class FeatureCache {

    private static Map<String, Object> CACHE = new HashMap<String, Object>();

    public static void put(String key, Object value) {
        CACHE.put(key, value);
    }

    public static Object get(String key) {
        return CACHE.get(key);
    }

    public static void clear() {
        CACHE.clear();
    }
}
