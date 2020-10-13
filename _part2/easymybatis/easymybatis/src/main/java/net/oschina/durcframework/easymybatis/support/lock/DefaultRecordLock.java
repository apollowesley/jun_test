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

/**
 * 默认记录锁
 * @author tanghc
 *
 */
public class DefaultRecordLock implements RecordLock {

	private static KeyLockPool LOCK_POOL = new KeyLockPool();

	@Override
	public void lock(Object record) {
		String key = getKey(record);
		KeyLock lock = LOCK_POOL.getLock(key);
		lock.lock();
	}

	@Override
	public void unlock(Object record) {
		String key = getKey(record);
		KeyLock lock = LOCK_POOL.getLock(key);
		lock.unlock();
	}

	protected String getKey(Object record) {
		String className = record.getClass().getName();
		int hashCode = record.hashCode();

		return className + hashCode;
	}

}