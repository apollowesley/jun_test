package com.niki.redis;

import redis.clients.jedis.ShardedJedis;

public interface ShardedJedisCallback<V> {
    V execute(ShardedJedis shardedJedis);
}
