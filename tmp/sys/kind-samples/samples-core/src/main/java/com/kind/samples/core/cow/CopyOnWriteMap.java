package com.kind.samples.core.cow;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {

    private volatile Map<K, V> internalMap;

    public CopyOnWriteMap() {
        internalMap = new HashMap<K, V>();
    }

    public CopyOnWriteMap(int size) {
        internalMap = new HashMap<K, V>(size);
    }

    public int size() {
        return internalMap.size();
    }

    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return internalMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return internalMap.containsValue(value);
    }

    public V get(Object key) {
        return internalMap.get(key);
    }

    public V put(K key, V value) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            V val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        }

    }

    public V remove(Object key) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            V val = newMap.remove(key);
            internalMap = newMap;
            return val;
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            newMap.putAll(m);
            internalMap = newMap;
        }

    }

    public void clear() {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            newMap.clear();
            internalMap = newMap;
        }
    }

    public Set<K> keySet() {
        return internalMap.keySet();
    }

    public Collection<V> values() {
        return internalMap.values();
    }

    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return internalMap.entrySet();
    }


}
