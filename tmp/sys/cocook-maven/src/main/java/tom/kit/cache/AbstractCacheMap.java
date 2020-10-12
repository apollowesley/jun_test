// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package tom.kit.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Default implementation of timed and size cache map.
 * Implementations should:
 * <ul>
 * <li>create a new cache map</li>
 * <li>implements own <code>prune</code> strategy</li>
 * </ul>
 * Uses <code>ReentrantReadWriteLock</code> to synchronize access.
 * Since upgrading from a read lock to the write lock is not possible,
 * be careful withing {@link #get(Object)} method.
 */
public abstract class AbstractCacheMap<K,V> implements Cache<K,V> {

	class CacheObject<K2,V2> {
		CacheObject(K2 key, V2 object, long ttl) {
			this.key = key;
			this.cachedObject = object;
			this.ttl = ttl;
			this.lastAccess = System.currentTimeMillis();
		}

		final K2 key;
		final V2 cachedObject;
		long lastAccess;		// time of last access
		long accessCount;		// number of accesses
		long ttl;				// objects timeout (time-to-live), 0 = no timeout

		boolean isExpired() {
			if (ttl == 0) {
				return false;
			}
			return lastAccess + ttl < System.currentTimeMillis();
		}
		V2 getObject() {
			lastAccess = System.currentTimeMillis();
			accessCount++;
			return cachedObject;
		}
    }

	protected Map<K,CacheObject<K,V>> cacheMap;

// 废弃这种方式, 多线程的时候效率很差, 使用同步类 加 synch()的 方式
//	private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
//	private final Lock readLock = cacheLock.readLock();
//	private final Lock writeLock = cacheLock.writeLock();


	// ---------------------------------------------------------------- properties

	protected int cacheSize;      // max cache size, 0 = no limit

	/**
	 * {@inheritDoc}
	 */
	public int getCacheSize() {
		return cacheSize;
	}

	protected long timeout;     // default timeout, 0 = no timeout

	/**
	 * Returns default cache timeout or <code>0</code> if it is not set.
	 * Timeout can be set individually for each object.
	 */
	public long getCacheTimeout() {
		return timeout;
	}

	/**
	 * Identifies if objects has custom timeouts.
	 * Should be used to determine if prune for existing objects is needed.
	 */
	protected boolean existCustomTimeout;

	/**
	 * Returns <code>true</code> if prune of expired objects should be invoked.
	 * For internal use.
	 */
	protected boolean isPruneExpiredActive() {
		return (timeout != 0) || existCustomTimeout;
	}


	// ---------------------------------------------------------------- put


	/**
	 * {@inheritDoc}
	 */
	public void put(K key, V object) {
		put(key, object, timeout);
	}


	/**
	 * {@inheritDoc}
	 */
	public void put(K key, V object, long timeout) {
		synchronized (cacheMap) {
			CacheObject<K,V> co = new CacheObject<>(key, object, timeout);
			if (timeout != 0) {
				existCustomTimeout = true;
			}
			if (isReallyFull(key)) {
				pruneCache();
			}
			cacheMap.put(key, co);
		}
	}


	// ---------------------------------------------------------------- get

	protected int hitCount;
	protected int missCount;

	/**
	 * Returns hit count.
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * Returns miss count.
	 */
	public int getMissCount() {
		return missCount;
	}
	
	
	public V get(K key) {
		synchronized (cacheMap) {
			CacheObject<K, V> co = cacheMap.get(key);
			if (co == null) {
				missCount++;
				return null;
			}
			if (co.isExpired()) {
				// remove(key); // can't upgrade the lock
				cacheMap.remove(key);

				missCount++;
				return null;
			}

			hitCount++;
			return co.getObject();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	/*public V get(K key) {
		readLock.lock();

		try {
			CacheObject<K,V> co = cacheMap.get(key);
			if (co == null) {
				missCount++;
				return null;
			}
			if (co.isExpired()) {
				// remove(key);		// can't upgrade the lock
				cacheMap.remove(key);

				missCount++;
				return null;
			}

			hitCount++;
			return co.getObject();
		}
		finally {
			readLock.unlock();
		}
	}*/


	// ---------------------------------------------------------------- prune

	/**
	 * Prune implementation.
	 */
	protected abstract int pruneCache();

	/**
	 * {@inheritDoc}
	 */
	public final int prune() {
		return pruneCache();
	}

	// ---------------------------------------------------------------- common

	/**
	 * {@inheritDoc}
	 */
	public boolean isFull() {
		if (cacheSize == 0) {
			return false;
		}
		return cacheMap.size() >= cacheSize;
	}

	protected boolean isReallyFull(K key) {
		if (cacheSize == 0) {
			return false;
		}
		if (cacheMap.size() >= cacheSize) {
			return !cacheMap.containsKey(key);
		}
		else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public V remove(K key) {
		CacheObject<K,V> cacheObject = cacheMap.remove(key);
		return  cacheObject == null? null :cacheObject.getObject();
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		cacheMap.clear();
	}


	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return cacheMap.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
}
