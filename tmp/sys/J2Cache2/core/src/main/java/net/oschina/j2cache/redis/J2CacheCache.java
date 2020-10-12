/**
 * 
 */
package net.oschina.j2cache.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import net.oschina.j2cache.CacheChannel;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月27日
 */
public class J2CacheCache implements Cache {

	private String cacheName;
	private CacheChannel cacheChannel;
	
	public J2CacheCache(String cacheName,CacheChannel cacheChannel) {
		super();
		this.cacheName = cacheName;
		this.cacheChannel = cacheChannel;
	}

	@Override
	public String getName() {
		return cacheName;
	}

	@Override
	public Object getNativeCache() {
		return cacheChannel;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object object = cacheChannel.get(cacheName, key);
		return object != null ?new SimpleValueWrapper(object) : null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		ValueWrapper wrapper = get(key);
		return (T) wrapper.get();
	}

	@Override
	public void put(Object key, Object value) {
		cacheChannel.set(cacheName, key, value);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		cacheChannel.set(cacheName, key, value);
		Object object = cacheChannel.get(cacheName, key);
		return object != null ?new SimpleValueWrapper(object) : null;
	}

	@Override
	public void evict(Object key) {
		cacheChannel.evict(cacheName, key);
	}

	@Override
	public void clear() {
		cacheChannel.clear(cacheName);
	}


}
