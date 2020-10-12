package com.iotechn.iot.executor.dev;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-28
 * Time: 下午5:36
 */
public class CacheMockImpl implements ICache {

    private Map<String,String> cacheMap = new HashMap<String,String>();

    public void set(String key, String value) {
        cacheMap.put(key,value);
    }

    public void inc(String key, long delta) {
        String s = cacheMap.get(key);
        cacheMap.put(key,new Long(s) + delta+"");
    }

    public void inc(String key, double delta) {
        String s = cacheMap.get(key);
        cacheMap.put(key,new Double(s) + delta+"");
    }

    public String get(String key) {
        return cacheMap.get(key);
    }
}
