package net.oschina.j2cache.hibernate4;

public interface CacheRegion {
    
    String getName();

    void clear();

    Object get(Object key);

    void put(Object key, Object value);

    void evict(Object key);

    Iterable<? extends Object> keys();

}
