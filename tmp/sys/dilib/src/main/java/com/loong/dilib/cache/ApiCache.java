package com.loong.dilib.cache;

import java.lang.reflect.Method;

/**
 * Api缓存处理类
 *
 * @author 张成轩
 */
public interface ApiCache {

	/**
	 * 构建缓存键
	 * 
	 * @param api Api类实例对象
	 * @param method Api方法
	 * @param params 访问参数
	 * @return 缓存键
	 */
	public String buildKey(Object api, Method method, Object[] params);

	/**
	 * 获取缓存
	 * 
	 * @param api Api类实例对象
	 * @param method Api方法
	 * @param key 键
	 * @return 响应信息
	 */
	public String get(Object api, Method method, String key);

	/**
	 * 添加缓存
	 * 
	 * @param api Api类实例对象
	 * @param method Api方法
	 * @param key 键
	 * @param html 响应信息
	 * @param expire 过期时间（秒）
	 */
	public void put(Object api, Method method, String key, String html, int expire);
}
