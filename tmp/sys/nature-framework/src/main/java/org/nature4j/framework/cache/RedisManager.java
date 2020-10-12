package org.nature4j.framework.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nature4j.framework.util.JedisUtil;
import org.nature4j.framework.util.SerializeUtil;

import redis.clients.jedis.ShardedJedis;

public class RedisManager implements NatureCacheManager {
	static SerializeUtil defaultSerialize;
	private static RedisManager redisManager = new RedisManager();
	public static RedisManager getInstance() {
		if(defaultSerialize==null)
			defaultSerialize = SerializeUtil.getInstance();
		return redisManager;
	}
	public void put(String cacheName, String key, Object value) {
		ShardedJedis resource = JedisUtil.getShardedJedis();
		Map<byte[], byte[]> hash = new HashMap<byte[], byte[]>();
		hash.put(key.getBytes(), defaultSerialize.serilize(value));
		resource.hmset(cacheName.getBytes(), hash);
		resource.close();
		
	}
	public Object get(String cacheName, String key) {
		ShardedJedis resource = JedisUtil.getShardedJedis();
		List<byte[]> list = resource.hmget(cacheName.getBytes(), key.getBytes());
		resource.close();
		return defaultSerialize.deserilize(list.get(0));
	}
	public void flush(String cacheName) {
		ShardedJedis resource = JedisUtil.getShardedJedis();
		resource.del(cacheName);
		resource.close();
	}
	
	public Object destroy() {
		return null;
	}

	public boolean remove(String cacheName, String key) {
		ShardedJedis resource = JedisUtil.getShardedJedis();
		Long hdel = resource.hdel(cacheName.getBytes(), key.getBytes());
		return hdel>0?true:false;
	}

}