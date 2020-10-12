package org.nature4j.framework.cache;

public interface NatureCacheManager {
	public void put(String cacheName,String key,Object value) ;
	public Object get(String cacheName,String key);
	public boolean remove(String cacheName,String key);
	public void flush(String cacheName);
	public Object destroy();
}
