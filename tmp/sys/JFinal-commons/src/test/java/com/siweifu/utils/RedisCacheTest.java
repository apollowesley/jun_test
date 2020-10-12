package com.siweifu.utils;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.siweifu.ext.activerecord.cache.RedisCache;

public class RedisCacheTest {

	public static void main(String[] args) {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://127.0.0.1:3306/jnode?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
		ds.setUser("root");
		ds.setPassword("root");
		
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin(ds);
		


//		arpMysql.setCache(redisCache);
		arpMysql.start();
		
		new RedisPlugin("main", "127.0.0.1").start();
		
		RedisCache redisCache = new RedisCache();
		redisCache.setDefaultExpiration(5 * 60); // 设置缓存5分钟，该参数默认为0，永不超时
		redisCache.intoDb();
		
		
		
		// 缓存 cacheName格式 cacheName#timeOut
		redisCache.put("hhh#300", 111, "xxxooo");
		
		System.out.println(redisCache.get("hhhh", 111));
		redisCache.put("hhhh#300", 111, "xxxooo");
		
		System.out.println(redisCache.get("hhhh", 111));
		
		redisCache.removeAll("hhh");
	}
}
