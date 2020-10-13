package tom.cache;

import java.io.Serializable;

/**
 * 缓存助手
 * @author Winter Lau
 */
public class EHCacheManager {
	
	private static CacheProvider provider;

	static {
		initCacheProvider("tom.cache.EHCacheProvider"); // 可用于扩展, 默认
	}
	
	private static void initCacheProvider(String prv_name){
		try{
			provider = (CacheProvider)Class.forName(prv_name).newInstance();
			provider.start();
		}catch(Exception e){
			provider = new EHCacheProvider();
			provider.start();
		}
	}

	private final static EHCache getCache(String cache_name, boolean autoCreate) {
		if(provider == null){
			provider = new EHCacheProvider();
		}
		return provider.buildCache(cache_name, autoCreate);
	}

	/**
	 * 获取缓存中的数据
	 * @param name
	 * @param key
	 * @return
	 */
	public final static Object get(String name, Serializable key){
		//System.out.println("GET1 => " + name+":"+key);
		if(name!=null && key != null)
			return getCache(name, true).get(key);
		return null;
	}
	
	/**
	 * 获取缓存中的数据
	 * @param <T>
	 * @param resultClass
	 * @param name
	 * @param key
	 * @return
	 */
	public final static <T> T get(Class<T> resultClass, String name, Serializable key){
		//System.out.println("GET2 => " + name+":"+key);
		if(name!=null && key != null)
			return resultClass.cast(getCache(name, true).get(key)) ;
		return null;
	}
	
	/**
	 * 写入缓存
	 * @param name
	 * @param key
	 * @param value
	 */
	public final static void set(String name, Serializable key, Serializable value){
		//System.out.println("SET => " + name+":"+key+"="+value);
		if(name!=null && key != null && value!=null)
			getCache(name, true).put(key, value);		
	}
	
	/**
	 * 清除缓冲中的某个数据
	 * @param name
	 * @param key
	 */
	public final static void evict(String name, Serializable key){
		if(name!=null && key != null)
			getCache(name, true).remove(key);		
	}

	/**
	 * 清除缓冲中的某个数据
	 * @param name
	 * @param key
	 */
	public final static void justEvict(String name, Serializable key){
		if(name!=null && key != null){
			EHCache cache = getCache(name, false);
			if(cache != null)
				cache.remove(key);
		}
	}
	
	
	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		EHCacheManager.set("default", "aa", "hello world!!!");
		System.out.println(EHCacheManager.get("default", "aa")); ;
		System.out.println(System.currentTimeMillis() - a);
	}
}
