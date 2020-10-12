/**
 * 
 */
package net.oschina.j2cache.hibernate4.cache;

import org.springframework.cache.Cache;

import net.oschina.j2cache.redis.J2CacheManager;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年11月1日
 */
public class J2CacheFixedLazyManager extends J2CacheManager{

	@Override
	protected Cache createAndAddCache(String cacheName) {
		addCache(new J2CacheFixedLazyCache(cacheName,getCacheChannel()));
		return super.getCache(cacheName);
	}
}
