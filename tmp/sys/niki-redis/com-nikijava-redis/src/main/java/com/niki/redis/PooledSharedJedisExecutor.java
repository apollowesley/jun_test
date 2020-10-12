package com.niki.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class PooledSharedJedisExecutor implements ShardedJedisExecutor {

    private final ShardedJedisPool shardedJedisPool;

    PooledSharedJedisExecutor(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    @Override
    public <V> V execute(ShardedJedisCallback<V> cb) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return cb.execute(shardedJedis);
        } catch (JedisException e) {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
            throw e;
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }

        }
    }

}
