package org.nature.framework.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhcacheManager implements NatureCacheManager {
	private static EhcacheManager ehcacheManager = new EhcacheManager();
	private static CacheManager cacheManager ;
	public static EhcacheManager getInstance() {
		cacheManager = CacheManager.getInstance();
		return ehcacheManager;
	}
	
	public Ehcache addCacheIfAbsent(String cacheName){
		return cacheManager.addCacheIfAbsent(cacheName);
	}
	
	public void put(String cacheName,String key,Object value){
		addCacheIfAbsent(cacheName).put(new Element(key, value));
	}
	
	public Object get(String cacheName,String key){
		Element element = addCacheIfAbsent(cacheName).get(key);
		return element!=null?element.getObjectValue():null;
	}
	
	public void flush(String cacheName) {
		addCacheIfAbsent(cacheName).flush();
	}

	public Object destroy() {
		cacheManager.shutdown();
		return null;
	}

}
