package com.siweifu.ext.activerecord.cache;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import redis.clients.jedis.Jedis;

/**
 * activerecord 的 redis cache插件
 * @author L.cm
 */
public class RedisCache implements ICache {

	private final Cache cache;
	// cacheName 和 key 的分隔符
	private String keyPrefix = ":";
	// 0 - never expire
	private int defaultExpiration = 0;

	// 默认字符集
	private final static Charset charset = Charset.forName("UTF-8");
	// lua 删除脚本
	private static final byte[] REMOVE_KEYS_BY_PATTERN_LUA = "local keys = redis.call('KEYS', ARGV[1]); local keysCount = table.getn(keys); if(keysCount > 0) then for _, key in ipairs(keys) do redis.call('del', key); end; end; return keysCount;".getBytes(charset);

	/**
	 * 默认使用main cache
	 */
	public RedisCache() {
		cache = Redis.use();
	}

	/**
	 * 自定义cacheName
	 * @param cacheName
	 */
	public RedisCache(String cacheName) {
		this.cache = Redis.use(cacheName);
	}
	
	/**
	 * 自定义的redis cache
	 * @param cache
	 */
	public RedisCache(Cache cache) {
		this.cache = cache;
	}

	@Override
	public <T> T get(String cacheName, Object key) {
		String cacheKey = buildCacheKey(cacheName, key);
		return cache.get(cacheKey);
	}

	@Override
	public void put(String cacheName, Object key, Object value) {
		String[] split = cacheName.split("#");
		
		int seconds = split.length > 1 
				? Integer.parseInt(split[1]) 
				: defaultExpiration;
		
		String cacheKey = buildCacheKey(cacheName, key);
		if (seconds > 0) {
			cache.setex(cacheKey, seconds, value);
		} else {
			cache.set(cacheKey, value);
		}
	}

	@Override
	public void remove(String cacheName, Object key) {
		String cacheKey = buildCacheKey(cacheName, key);
		cache.del(cacheKey);
	}

	@Override
	public void removeAll(String cacheName) {
		Jedis jedis = cache.getJedis();
		String params = buildRemoveAllParams(cacheName);
		try {
			jedis.eval(REMOVE_KEYS_BY_PATTERN_LUA, 0, params.getBytes(charset));
		}
		finally {cache.close(jedis);}
	}

	/**
	 * 配置key分割符
	 * @param keyPrefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	/**
	 * 设置默认的缓存时间，默认为0不设置默认
	 * @param defaultExpiration
	 */
	public void setDefaultExpiration(int defaultExpiration) {
		this.defaultExpiration = defaultExpiration;
	}

	/**
	 * 构建redis缓存的 cache key
	 * @param cacheName 缓存名
	 * @param key 缓存key
	 * @return 缓存最终key
	 */
	private String buildCacheKey(String cacheName, Object key) {
		cacheName = cacheName.split("#")[0];
		String keyStr = cache.getKeyNamingPolicy().getKeyName(key);
		
		return new StringBuilder(cacheName)
				.append(keyPrefix)
				.append(keyStr)
				.toString();
	}

	/**
	 * 构建redis缓存的 cache key
	 * @param cacheName 缓存名
	 * @return 缓存删除参数
	 */
	private String buildRemoveAllParams(String cacheName) {
		cacheName = cacheName.split("#")[0];
		return new StringBuilder(cacheName)
		.append(keyPrefix)
		.append("*")
		.toString();
	}

	/**
	 * 直接反射进入db配置
	 */
	public void intoDb() {
		Config config = DbKit.getConfig();
		reflectConfig(config);
	}
	/**
	 * 直接反射进入db配置
	 */
	public void intoDb(String configName) {
		Config config = DbKit.getConfig(configName);
		reflectConfig(config);
	}

	private void reflectConfig(Config config) {
		try {
			Field field = config.getClass().getDeclaredField("cache");
			field.setAccessible(true);
			field.set(config, this);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
