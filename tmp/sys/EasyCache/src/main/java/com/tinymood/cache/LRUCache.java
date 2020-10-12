package com.tinymood.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * LRU(Least Recently Used)Cache实现
 * <b>Note:</b>该实现是线程安全的
 *
 * @param <K> key
 * @param <V> value
 *
 * @author hztaoran
 * @version 1.0
 */
public class LRUCache<K, V> implements Cache<K, V> {

    private static final int REMOVE_ALL = -1;

    private static final int DEFAULT_CAPACITY = 10;

    private final Map<K,V> map;

    private final int maxMemorySize;

    private int memorySize = 0;

    private static final Logger logger = LoggerFactory.getLogger(LRUCache.class);

    public LRUCache() {
        this(DEFAULT_CAPACITY);
    }

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        this.map = new LRUHashMap<K, V>(capacity);
        maxMemorySize = 2 * 1024 * 1024;
    }

    @Override
    public final V get(K key) {
        Objects.requireNonNull(key, "key is null");
        synchronized (this) {
            V value = map.get(key);
            if (null != value) {
                return value;
            }
        }
        return null;
    }

    @Override
    public final V put(K key, V value) {
        Objects.requireNonNull(key, "key is null");
        Objects.requireNonNull(value, "value is null");
        V oldValue;
        synchronized (this) {
            oldValue = map.put(key, value);
            memorySize += getValueSize(value);
            if (null != oldValue) {
                memorySize -= getValueSize(oldValue);
            }
            trimToSize(maxMemorySize);
        }
        return oldValue;
    }

    @Override
    public final V remove(K key) {
        Objects.requireNonNull(key, "key is null");
        V oldValue;
        synchronized (this) {
            oldValue = map.remove(key);
            if (null != oldValue) {
                memorySize -= getValueSize(oldValue);
            }
        }
        return oldValue;
    }

    @Override
    public synchronized final void clear() {
        trimToSize(REMOVE_ALL);
    }

    @Override
    public synchronized final int getMaxMemorySize() {
        return maxMemorySize;
    }

    @Override
    public synchronized final int getMemorySize() {
        return memorySize;
    }

    /**
     * Returns a copy of the current contents of the cache.
     */
    public synchronized final Map<K, V> snapshot() {
        return new LinkedHashMap<>(map);
    }

    /**
     * Returns the size of the entry.
     * <p>
     * The default implementation returns 1 so that max size is the maximum number of entries.
     * <p>
     * <em>Note:</em> This method should be overridden if you control memory size correctly.
     *
     * @param value value
     * @return the size of the entry.
     */
    protected int getValueSize(V value) {
        return 1;
    }


    /**
     * Returns the class name.
     * <p>
     * This method should be overridden to debug exactly.
     *
     * @return class name.
     */
    protected String getClassName() {
        return LRUCache.class.getName();
    }

    /**
     * Remove the eldest entries.
     * <p>
     * <em>Note:</em> This method has to be called in synchronized block.
     *
     * @param maxSize
     */
    private void trimToSize(int maxSize) {
        while (true) {
            if (memorySize <= maxSize || map.isEmpty()) {
                break;
            }
            if (memorySize < 0 || (map.isEmpty() && memorySize != 0)) {
                throw new IllegalStateException(getClassName() + ".getValueSize() is reporting inconsistent results");
            }
            Map.Entry<K, V> toRemove = map.entrySet().iterator().next();
            map.remove(toRemove.getKey());
            memorySize -= getValueSize(toRemove.getValue());
        }
    }

    @Override
    public synchronized final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append(",");
        }
        sb.append("maxMemorySize=")
                .append(maxMemorySize)
                .append(",")
                .append("memorySize=")
                .append(memorySize)
                .append("]");
        return sb.toString();
    }
}
