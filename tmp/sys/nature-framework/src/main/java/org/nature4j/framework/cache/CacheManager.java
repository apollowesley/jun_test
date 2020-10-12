package org.nature4j.framework.cache;

import org.nature4j.framework.helper.ConfigHelper;

public class CacheManager {
	public static NatureCacheManager getCacheManager(){
		if ("ehcache".equalsIgnoreCase(ConfigHelper.getCacheManager())) {
			return EhcacheManager.getInstance();
		}else if ("redis".equalsIgnoreCase(ConfigHelper.getCacheManager())) {
			return RedisManager.getInstance();
		}else{
			return DefaultCacheManager.getInstance();
		}
	}
}
