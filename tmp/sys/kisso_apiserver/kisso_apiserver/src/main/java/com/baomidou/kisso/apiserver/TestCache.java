/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.apiserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.baomidou.kisso.apiserver.vo.CacheToken;

/**
 * 生产环境不要用，这里测试使用
 */
public class TestCache  {

	private static Map<String, CacheToken> cache = new ConcurrentHashMap<String, CacheToken>();

	public static CacheToken get(String key) {
		System.out.println("缓存中获取 CacheToken key=" + key);
		return cache.get(key);
	}

	public static boolean set(String key, CacheToken token, int expires) {
		System.out.println(" 添加缓存 CacheToken key=" + key);
		cache.put(key, token);
		return true;
	}

	public static boolean delete(String key) {
		System.out.println(" 缓存中删除 CacheToken key=" + key);
		cache.remove(key);
		return true;
	}

}
