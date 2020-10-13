/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.support.lock;

import org.apache.commons.collections.map.LRUMap;

/**
 * 关键字锁缓冲池
 * @author tanghc
 *
 */
public class KeyLockPool {
	private static final int poolSize = 10000;
	// cache记录已经创建过的对象
	private LRUMap cache = new LRUMap(poolSize);

	public synchronized KeyLock getLock(String key) {
		KeyLock lock = (KeyLock) this.cache.get(key);
		if (lock == null) {
			lock = new KeyLock(key);
			this.cache.put(key, lock);
		}
		return lock;
	}
}