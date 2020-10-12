/**
 * 
 */
package net.oschina.j2cache.redis;

import java.util.Collection;
import java.util.Collections;

import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.stereotype.Component;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月27日
 */
@Component
public class J2CacheManager extends AbstractTransactionSupportingCacheManager{

	private CacheChannel cacheChannel;

	public CacheChannel getCacheChannel() {
		if(cacheChannel == null)cacheChannel = J2Cache.getChannel();
		return cacheChannel;
	}

	public J2CacheManager() {}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return Collections.<Cache> emptyList();
	}

	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			return createAndAddCache(name);
		}
		return cache;
	}

	protected Cache createAndAddCache(String cacheName) {
		addCache(new J2CacheCache(cacheName,getCacheChannel()));
		return super.getCache(cacheName);
	}

	@Override
	protected Cache getMissingCache(String name) {
		return null;
	}

}
