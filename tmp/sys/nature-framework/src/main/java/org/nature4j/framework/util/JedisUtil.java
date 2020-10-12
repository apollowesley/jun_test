package org.nature4j.framework.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nature4j.framework.bean.RedisHostBean;
import org.nature4j.framework.helper.ConfigHelper;

import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class JedisUtil {
	static ShardedJedisPool shardedJedisPool = null;
	private static BinaryJedisCluster binaryJedisCluster;
	public static ShardedJedis getShardedJedis() {
		if (shardedJedisPool==null) {
			shardedJedisPool=getShardedJedisPool();
		}
		return shardedJedisPool.getResource();
	}
	
	public static BinaryJedisCluster getClusterJedis() {
		if (binaryJedisCluster==null) {
			binaryJedisCluster=getBinaryJedisCluster();
		}
		return binaryJedisCluster;
	}

	private static ShardedJedisPool getShardedJedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(ConfigHelper.getRedisMaxIdle());
		config.setMinIdle(ConfigHelper.getRedisMinIdle());
		config.setMaxWaitMillis(ConfigHelper.getRedisMaxWaitMillis());
		config.setMaxTotal(ConfigHelper.getRedisMaxTotal());
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>();
		List<RedisHostBean> redisHostPortMap = ConfigHelper.getRedisHostPortMap();
		for (RedisHostBean redisHostBean : redisHostPortMap) {
			JedisShardInfo infoA = new JedisShardInfo(redisHostBean.getHost(), redisHostBean.getPort());
			if(StringUtil.isNotEmpty(redisHostBean.getPassword())){
				infoA.setPassword(redisHostBean.getPassword());
			}
			jdsInfoList.add(infoA);
		}
		ShardedJedisPool pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
		return pool;
	}

	private static BinaryJedisCluster  getBinaryJedisCluster() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(ConfigHelper.getRedisMaxIdle());
		config.setMinIdle(ConfigHelper.getRedisMinIdle());
		config.setMaxWaitMillis(ConfigHelper.getRedisMaxWaitMillis());
		config.setMaxTotal(ConfigHelper.getRedisMaxTotal());
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();  
		List<RedisHostBean> redisHostPortMap = ConfigHelper.getRedisHostPortMap();
		for (RedisHostBean redisHostBean : redisHostPortMap) {
			HostAndPort hostAndPort = new HostAndPort(redisHostBean.getHost(), redisHostBean.getPort());
			jedisClusterNodes.add(hostAndPort);
		}
		return	new BinaryJedisCluster(jedisClusterNodes,5,5,config);  
	}
	

}
