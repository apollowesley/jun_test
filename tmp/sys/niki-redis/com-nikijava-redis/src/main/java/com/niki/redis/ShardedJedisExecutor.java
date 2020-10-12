package com.niki.redis;

public interface ShardedJedisExecutor {
    <V> V execute(ShardedJedisCallback<V> cb);
}
