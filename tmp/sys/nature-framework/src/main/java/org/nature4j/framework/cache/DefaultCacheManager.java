package org.nature4j.framework.cache;

import java.util.HashMap;
import java.util.Map;

public class DefaultCacheManager implements NatureCacheManager {
	private Map<String,Map<String,Object>> cache = new HashMap<String,Map<String,Object>>();
	
	private static DefaultCacheManager cacheManager ;
	public static DefaultCacheManager getInstance() {
		if(cacheManager==null)
			cacheManager = new DefaultCacheManager();
		return cacheManager;
	}
	
	public void put(String cacheName, String key, Object value) {
		Map<String, Object> map = cache.get(cacheName);
		if(map==null){
			map = new HashMap<String,Object>();
		}
		map.put(key, value);
		cache.put(cacheName, map);
	}

	public Object get(String cacheName, String key) {
		Map<String, Object> map = cache.get(cacheName);
		if(map!=null){
			return map.get(key);
		}
		return null;
	}

	public boolean remove(String cacheName, String key) {
		Map<String, Object> map = cache.get(cacheName);
		if(map!=null){
			map.remove(key);
			return true;
		}
		return false;
	}

	public void flush(String cacheName) {
		Map<String, Object> map = cache.get(cacheName);
		if(map!=null){
			map.clear();
		}
	}

	public Object destroy() {
		cache.clear();
		return null;
	}
	
}
