package org.nature.framework.cache;

public interface NatureCacheManager {
	public void put(String cacheName,String key,Object value) ;
	public Object get(String cacheName,String key);
	public void flush(String cacheName);
	public Object destroy();
}
