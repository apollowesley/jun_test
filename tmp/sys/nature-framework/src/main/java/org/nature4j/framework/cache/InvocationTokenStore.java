package org.nature4j.framework.cache;

import java.util.HashMap;
import java.util.Map;

import org.nature4j.framework.util.CastUtil;

public class InvocationTokenStore {
	private static String TOKEN_MAP = "TOKEN_MAP";
	private static String TOKEN_KEY = "TOKEN_KEY";
	
	public static void put(String token,InvocationContext invocationContext) {
		Map<String, Object> map = getInvocationMap();
		map.put(token, invocationContext.serialize());
		setInvocationMap(map);
	}

	public static void put(String value) {
		Map<String, Object> map = getInvocationMap();
		map.put(TOKEN_KEY, value);
		setInvocationMap(map);
	}
	
	public static String getToken() {
		Map<String, Object> map = getInvocationMap();
		return CastUtil.castString(map.get(TOKEN_KEY));
	}

	private static void setInvocationMap(Map<String, Object> map) {
		String key = NatureContext.getRequest().getSession().getId();
		NatureCacheManager cacheManager = CacheManager.getCacheManager();
		cacheManager.put(TOKEN_MAP, key, map);
	} 

	@SuppressWarnings("unchecked")
	private static Map<String, Object> getInvocationMap() {
		String key = NatureContext.getRequest().getSession().getId();
		NatureCacheManager cacheManager = CacheManager.getCacheManager();
		Object object = cacheManager.get(TOKEN_MAP,key);
		if (object==null) {
			object = new HashMap<String, Object>();
		}
		return (Map<String, Object>) object;
	}

	public static InvocationContext getInvocation(String token) {
		Map<String, Object> invocationMap = getInvocationMap();
		return (InvocationContext) invocationMap.get(token);
	}

	public static void remove() {
		Map<String, Object> invocationMap = getInvocationMap();
		invocationMap.remove(TOKEN_KEY);
	}
	
	public static void removeAll() {
		NatureCacheManager cacheManager = CacheManager.getCacheManager();
		cacheManager.flush(TOKEN_MAP);
	}
	
	
}
