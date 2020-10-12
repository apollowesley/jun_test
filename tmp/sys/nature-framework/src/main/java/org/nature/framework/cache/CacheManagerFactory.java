package org.nature.framework.cache;

import org.nature.framework.helper.ConfigHelper;

public class CacheManagerFactory {
	public static NatureCacheManager getCacheManager(){
		if ("ehcache".equalsIgnoreCase(ConfigHelper.getCacheManager())) {
			return EhcacheManager.getInstance();
		}else if ("redis".equalsIgnoreCase(ConfigHelper.getCacheManager())) {
			return RedisManager.getInstance();
		}
		return null;
	}
}
