package net.oschina.j2cache;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import net.oschina.j2cache.ehcache.EhCacheProvider;
import net.oschina.j2cache.redis.RedisCacheProvider;

/**
 * 缓存管理器
 * @author Winter Lau
 */
public class CacheManager {

	private final static Logger log = LoggerFactory.getLogger(CacheManager.class);

	private static CacheProvider l1_provider;
	private static CacheProvider l2_provider;
	
	private static CacheExpiredListener listener;
	
	private static String serializer ;
	
	public static void initCacheProvider(CacheExpiredListener listener){
		initCacheProvider(listener, null);
	}
	/**
	 * Initialize Cache Provider
	 * @param listener cache listener
	 */
	public static void initCacheProvider(CacheExpiredListener listener,RedisTemplate<String ,Object> redisTemplate){

		
		CacheManager.listener = listener;
		try{

			Properties props = J2Cache.getConfig();
			
			if(J2Cache.isLevel1CacheEnabled()){
				CacheManager.l1_provider = new EhCacheProvider();
			}else{
				CacheManager.l1_provider = new NullCacheProvider();
			}
			CacheManager.l1_provider.start(getProviderProperties(props, CacheManager.l1_provider));
			log.info("Using L1 CacheProvider : " + l1_provider.getClass().getName());
			//
			if(redisTemplate == null){
				throw new NullPointerException("redisTemplate not init");
			}
			CacheManager.l2_provider = new RedisCacheProvider(redisTemplate);
			log.info("Using L2 CacheProvider : " + l2_provider.getClass().getName());
			
			CacheManager.serializer = props.getProperty("cache.serialization","kryo");
			
		}catch(Exception e){
			throw new CacheException("Unabled to initialize cache providers", e);
		}
	}
	
	public final static String getSerializer() {
		return serializer;
	}
	
	private final static Properties getProviderProperties(Properties props, CacheProvider provider) {
		Properties new_props = new Properties();
		Enumeration<Object> keys = props.keys();
		String prefix = provider.name() + '.';
		while(keys.hasMoreElements()){
			String key = (String)keys.nextElement();
			if(key.startsWith(prefix))
				new_props.setProperty(key.substring(prefix.length()), props.getProperty(key));
		}
		return new_props;
	}
	
	private final static Cache _GetCache(int level, String cache_name, boolean autoCreate) {
		return ((level==1)?l1_provider:l2_provider).buildCache(cache_name, autoCreate, listener);
	}
	
	public final static void shutdown(int level) {
		((level==1)?l1_provider:l2_provider).stop();
	}

	/**
	 * 获取缓存中的数据
	 * @param level Cache Level: L1 and L2
	 * @param name Cache region name
	 * @param key Cache key
	 * @return Cache object
	 */
	public final static Object get(int level, String name, Object key){
		//System.out.println("GET1 => " + name+":"+key);
		if(name!=null && key != null) {
            Cache cache = _GetCache(level, name, false);
            if (cache != null)
                return cache.get(key);
        }
		return null;
	}
	
	/**
	 * 获取缓存中的数据
	 * @param level Cache Level -&gt; L1 and L2
	 * @param resultClass Cache object class
	 * @param name Cache region name
	 * @param key Cache key
	 * @return Cache object
	 */
	@SuppressWarnings("unchecked")
	public final static <T> T get(int level, Class<T> resultClass, String name, Object key){
		//System.out.println("GET2 => " + name+":"+key);
		if(name!=null && key != null) {
            Cache cache =_GetCache(level, name, false);
            if (cache != null)
                return (T)cache.get(key);
        }
		return null;
	}
	
	/**
	 * 写入缓存
	 * @param level Cache Level: L1 and L2
	 * @param name Cache region name
	 * @param key Cache key
	 * @param value Cache value
	 */
	public final static void set(int level, String name, Object key, Object value){
		//System.out.println("SET => " + name+":"+key+"="+value);
		if(name!=null && key != null && value!=null) {
            Cache cache =_GetCache(level, name, true);
            if (cache != null)
                cache.put(key,value);
        }
	}
	
	/**
	 * 清除缓存中的某个数据
	 * @param level Cache Level: L1 and L2
	 * @param name Cache region name
	 * @param key Cache key
	 */
	public final static void evict(int level, String name, Object key){
		//batchEvict(level, name, java.util.Arrays.asList(key));
		if(name!=null && key != null) {
            Cache cache =_GetCache(level, name, false);
            if (cache != null)
                cache.evict(key);
        }
	}
	
	/**
	 * 批量删除缓存中的一些数据
	 * @param level Cache Level： L1 and L2
	 * @param name Cache region name
	 * @param keys Cache keys
	 */
	@SuppressWarnings("rawtypes")
	public final static void batchEvict(int level, String name, List keys) {
		if(name!=null && keys != null && keys.size() > 0) {
            Cache cache =_GetCache(level, name, false);
            if (cache != null)
                cache.batchEvict(keys);
        }
	}

	/**
	 * Clear the cache
	 * @param level Cache level
	 * @param name cache region name
	 */
	public final static void clear(int level, String name) throws CacheException {
        Cache cache =_GetCache(level, name, false);
        if(cache != null)
        	cache.clear();
	}
	
	/**
	 * list cache keys
	 * @param level Cache level
	 * @param name cache region name
	 * @return Key List
	 */
	@SuppressWarnings("rawtypes")
	public final static List keys(int level, String name) throws CacheException {
        Cache cache =_GetCache(level, name, false);
		return (cache!=null)?cache.keys():null;
	}
	
	public final static void batchEvict(int level, String name, Object... keyPatterns) {
		if(name!=null && keyPatterns != null && keyPatterns.length > 0) {
            Cache cache =_GetCache(level, name, false);
            if (cache != null)
                cache.batchEvict(keyPatterns);
        }
	}
	
}
