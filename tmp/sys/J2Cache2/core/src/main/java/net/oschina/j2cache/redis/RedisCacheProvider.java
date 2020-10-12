/**
 * 
 */
package net.oschina.j2cache.redis;

import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.CacheExpiredListener;
import net.oschina.j2cache.CacheProvider;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月26日
 */
@Component
@Lazy(false)
public class RedisCacheProvider implements CacheProvider {

	private RedisTemplate<String ,Object> redisTemplate;
	
	protected ConcurrentHashMap<String, RedisCache> caches = new ConcurrentHashMap<>();
	
	private ScheduledExecutorService clearExpiredRegionKeysTimer;
	
	public RedisCacheProvider(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
		//删除过期组keys集合
		clearExpiredRegionKeysTimer = Executors.newScheduledThreadPool(1);
		clearExpiredRegionKeysTimer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Collection<RedisCache> springCaches = caches.values();
				for (RedisCache springCache : springCaches) {
					springCache.clearExpiredRegionKeys();
				}
			}
		}, 5, 60, TimeUnit.MINUTES);
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	@Override
	public String name() {
		return "spring-data-redis";
	}
	

	@Override
	public Cache buildCache(String regionName, boolean autoCreate, CacheExpiredListener listener)
			throws CacheException {
		RedisCache cache = caches.get(regionName);
		if(cache == null){
			synchronized (caches) {
				cache = caches.containsKey(regionName) ? caches.get(regionName) : new RedisCache(regionName, redisTemplate);
				caches.put(regionName, cache);
			}
		}
		return cache;
	}


	@Override
	public void start(Properties props) throws CacheException {
		
	}

	@Override
	public void stop() {
		clearExpiredRegionKeysTimer.shutdown();
	}

}
