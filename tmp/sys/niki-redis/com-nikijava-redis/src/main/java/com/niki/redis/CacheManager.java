package com.niki.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CacheManager {


    private final ShardedJedisExecutor jedisExecutor;

    private final String SERIALIZABLE_CACHE_ = "SerializableCaheMAPPERKEY_";

    public CacheManager(ShardedJedisPool jedisPool) {
        jedisExecutor = new PooledSharedJedisExecutor(jedisPool);
    }

    /**
     * 获取当前key过期时间的剩余时间
     *
     * @param key
     * @return
     * @author Join
     */
    public Long getTTL(final String key) {

        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.ttl(key);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @param
     * @return
     * @method
     * @description 是否存在
     * @date: 2019/9/1 0001 16:16
     * @author: Niki Zheng
     */
    public Boolean exists(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.exists(key);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }


    /**
     * @method
     * @description 删除key
     * @date: 2019/9/1 0001 16:19
     * @author: Niki Zheng
     *@param
     * @return
     */
    public Boolean remove(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {

                try {
                    Long result = shardedJedis.del(key);
                    return (result != null && result.longValue() == 1) ? true : false;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }

    /**
     * @param
     * @return
     * @method
     * @description 保存string类型数据
     * @date: 2019/9/1 0001 16:17
     * @author: Niki Zheng
     */
    public Boolean saveString(final String key, final String dataString, final int seconds) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    String r = shardedJedis.set(key, dataString);
                    if (seconds > 0) {
                        shardedJedis.expire(key, seconds);
                    }
                    return "OK".equals(r) ? true : false;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }

    /**
     * @method
     * @description 获取string
     * @date: 2019/9/1 0001 16:21
     * @author: Niki Zheng
     *@param
     * @return
     */
    public String getString(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.get(key);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @param
     * @return
     * @method
     * @description 保存object对象数据
     * @date: 2019/9/1 0001 16:18
     * @author: Niki Zheng
     */
    public <T extends Serializable> void saveObject(final String key, final T data) {
        jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                String cacheName = SERIALIZABLE_CACHE_;
                try {
                    final byte[] keyByteArr = String.valueOf(key).getBytes();
                    final byte[] byteArr = SerializationUtils.serialize(data);
                    Long r = shardedJedis.hset(cacheName.getBytes(), keyByteArr, byteArr);
                    return r;

                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @method
     * @description 获取object数据
     * @date: 2019/9/1 0001 16:19
     * @author: Niki Zheng
     *@param
     * @return
     */
    public <T> T getObject(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T execute(ShardedJedis shardedJedis) {
                String cacheName = SERIALIZABLE_CACHE_;

                try {
                    final byte[] prefixByteArr = key.getBytes();
                    final byte[] byteArr = shardedJedis.hget(cacheName.getBytes(), prefixByteArr);
                    if (byteArr == null || byteArr.length == 0) {
                        return null;
                    }
                    return (T) SerializationUtils.deserialize(byteArr);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }



    /**
     * @method
     * @description 删除object数据
     * @date: 2019/9/1 0001 16:20
     * @author: Niki Zheng
     *@param
     * @return
     */
    public Boolean removeObject(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                String cacheName = SERIALIZABLE_CACHE_;

                try {
                    final byte[] prefixByteArr = key.getBytes();
                    Long result = shardedJedis.hdel(cacheName.getBytes(), prefixByteArr);
                    return (result != null && result.longValue() == 1) ? true : false;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }



    /**
     * @method
     * @description 获取分布式锁
     * @date: 2019/9/1 0001 16:24
     * @author: Niki Zheng
     *@param
     * @return
     */
    public Boolean tryLock(final String key, final int seconds) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    Long result = shardedJedis.setnx(key, "transfering");
                    if (result != null && result.longValue() == 1) {
                        if (seconds > 0) {
                            shardedJedis.expire(key, seconds);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }

    /**
     * @method
     * @description 接触分布式锁
     * @date: 2019/9/1 0001 16:24
     * @author: Niki Zheng
     *@param
     * @return
     */
    public Boolean unLock(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    Long result = shardedJedis.del(key);
                    if (result != null && result.longValue() == 1) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }




    /**
     * @method
     * @description list从左侧弹出string
     * @date: 2019/9/1 0001 16:25
     * @author: Niki Zheng
     *@param
     * @return
     */
    public String listPop(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                try {
                    String result = shardedJedis.lpop(key);
                    if (result != null && !"".equals(result)) {
                        return result;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * @method
     * @description list从右侧弹出string
     * @date: 2019/9/1 0001 16:26
     * @author: Niki Zheng
     *@param
     * @return
     */
    public String rPop(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                try {
                    String result = shardedJedis.rpop(key);
                    if (result != null && !"".equals(result)) {
                        return result;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * @method
     * @description 从右侧弹出对象
     * @date: 2019/9/1 0001 16:26
     * @author: Niki Zheng
     *@param
     * @return
     */
    public <T> T rPopObject(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T execute(ShardedJedis shardedJedis) {
                try {
                    byte[] result = shardedJedis.rpop(key.getBytes());
                    if (result != null && result.length != 0) {
                        return (T) SerializationUtils.deserialize(result);
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    log.error("deserialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @method
     * @description list从右侧放入String
     * @date: 2019/9/1 0001 16:27
     * @author: Niki Zheng
     *@param
     * @return
     */
    public void rPush(final String key, final String val) {
        jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    shardedJedis.rpush(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                }
                return null;

            }
        });
    }

    /**
     * @method
     * @description list从左侧推入String
     * @date: 2019/9/1 0001 16:27
     * @author: Niki Zheng
     *@param
     * @return
     */
    public void lPush(final String key, final String val) {
        jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    shardedJedis.lpush(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                }
                return null;

            }
        });
    }

    /**
     * @method
     * @description 左侧推入对象
     * @date: 2019/9/1 0001 16:28
     * @author: Niki Zheng
     *@param
     * @return
     */
    public void lPushObj(final String key, final Object data) {
        jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    final byte[] prefixByteArr = key.getBytes();
                    final byte[] byteArr = SerializationUtils.serialize(data);
                    shardedJedis.lpush(prefixByteArr, byteArr);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                }
                return null;

            }
        });
    }

    /**
     * @method
     * @description list从左侧弹出对象
     * @date: 2019/9/1 0001 16:29
     * @author: Niki Zheng
     *@param
     * @return
     */
    public <T> T listPopObj(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<T>() {
            @Override
            public T execute(ShardedJedis shardedJedis) {
                try {
                    final byte[] prefixByteArr = key.getBytes();
                    @SuppressWarnings("unchecked")
                    T t = (T) SerializationUtils.deserialize(shardedJedis.lpop(prefixByteArr));
                    if (t != null) {
                        return t;
                    } else {
                        return null;
                    }
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    public List<String> lRange(final String key) {
        return lRange(key, 0, -1);
    }

    // 默认取start-end
    public List<String> lRange(final String key, final long start, final long end) {
        return jedisExecutor.execute(new ShardedJedisCallback<List<String>>() {
            @Override
            public List<String> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.lrange(key, start, end);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    public String lIndex(final String key) {
        return lIndex(key, 0);
    }

    public String lIndex(final String key, final long index) {
        return jedisExecutor.execute(new ShardedJedisCallback<String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.lindex(key, index);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    public Long lLength(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.llen(key);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * 添加数据（根据score自动排序）
     *
     * @param key
     * @param score
     * @param member
     */
    public Long zadd(final String key, final Long score, final String member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long r = shardedJedis.zadd(key, score, member);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 添加数据（根据score自动排序）
     *
     * @param key
     * @param score
     * @param member
     */
    public Long zadd(final byte[] key, final Long score, final byte[] member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long r = shardedJedis.zadd(key, score, member);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + new String(key) + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 添加数据（根据score自动排序）
     *
     * @param key
     * @param score
     * @param member
     */
    public Long zadd(final String key, final Double score, final String member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long r = shardedJedis.zadd(key, score, member);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * @return Long
     * @throws
     * @Title: zadd
     * @Description: 一次插入所有集合的数据
     * @author zhengjianian
     * @date 2017年9月13日 上午11:07:45
     */
    public Long zadd(final String key, final Map<String, Double> menberMap) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long r = shardedJedis.zadd(key, menberMap);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 删除
     *
     * @param key
     * @param member
     */
    public Long zrem(final String key, final String member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long r = shardedJedis.zrem(key, member);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 删除
     *
     * @param key
     * @param member
     */
    public Long zrem(final String key, final byte[] member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long r = shardedJedis.zrem(key.getBytes("utf-8"), member);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangebyrank(final String key, final long start, final long end) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {

            @Override
            public Long execute(ShardedJedis shardedJedis) {

                return shardedJedis.zremrangeByRank(key, start, end);
            }

        });
    }

    /**
     * 删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangebyrank(final byte[] key, final long start, final long end) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {

            @Override
            public Long execute(ShardedJedis shardedJedis) {

                return shardedJedis.zremrangeByRank(key, start, end);
            }

        });
    }

    /**
     * 通过key查询区间的排序数据
     *
     * @param key
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public Set<String> zrangeByScore(final String key, final Long start, final Long end) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                try {
                    Set<String> r = shardedJedis.zrangeByScore(key, start, end);
                    return r;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 查询哈希表是否存在当前值
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(final String key, final String field) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.hexists(key, field);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return false;
                }
            }
        });
    }

    /**
     * 添加超时时间
     *
     * @param key
     * @param seconds
     */
    public void expire(final String key, final int seconds) {
        jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    if (seconds > 0) {
                        return shardedJedis.expire(key, seconds);// 设置过期时间为秒
                    } else {
                        return 0l;
                    }
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * incr操作，防止高并发环境下get再save引起数据错乱
     *
     * @param key
     */
    public Long incr(final String key, int seconds) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    Long rs = shardedJedis.incr(key);
                    if (seconds > 0) {
                        shardedJedis.expire(key, seconds);
                    }
                    return rs;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * incr操作，防止高并发环境下get再save引起数据错乱
     *
     * @param key
     */
    public Long incrby(final String key, final long val) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.incrBy(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * incrByFloat操作，防止高并发环境下get再save引起数据错乱
     *
     * @param key
     * @param val
     */
    public Double incrByFloat(final String key, final double val) {
        return jedisExecutor.execute(new ShardedJedisCallback<Double>() {
            @Override
            public Double execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.incrByFloat(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zincrby为有序集 key 的成员 member 的 score 值加上增量 increment
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(final String key, final double score, final String member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Double>() {
            @Override
            public Double execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zincrby(key, score, member);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrangeByScore返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
     *
     * @param key
     * @param min
     * @param max
     */
    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrangeByScore(key, min, max);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrevrangeByScore返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
     *
     * @param key
     * @param min
     * @param max
     */
    public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrevrangeByScore(key, max, min);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrangeByScore返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset,
                                     final int count) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrangeByScore(key, min, max, offset, count);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrevrangeByScore返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset,
                                        final int count) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrevrangeByScoreWithScores返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score
     * 值递减(从大到小)的次序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset,
                                                 final int count) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrevrangeByScoreWithScores返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score
     * 值递减(从大到小)的次序排列
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 查询有序集合中key对应的值
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(final String key, final String member) {
        return jedisExecutor.execute(new ShardedJedisCallback<Double>() {
            @Override
            public Double execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zscore(key, member);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrange通过索引区间返回有序集合成指定区间内的成员 有序集成员按 score 值递增(从小到大)次序排列
     *
     * @param key
     * @param
     * @return
     */
    public Set<String> zrange(final String key, final long start, final long end) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrange(key, start, end);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * zrange通过索引区间返回有序集合成指定区间内的成员 有序集成员按 score 值递增(从小到大)次序排列
     *
     * @param key
     * @param
     * @return
     */
    public Set<byte[]> zrange(final byte[] key, final long start, final long end) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrange(key, start, end);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 从redis有序集合中获取指定下标处的元素。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrangeWithScores(final byte[] key, final int start, final int end) {
        return jedisExecutor.execute(new ShardedJedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zrangeWithScores(key, start, end);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 获取zset中元素个数。
     *
     * @param key
     * @param
     * @param
     * @return
     */
    public Long zcard(final String key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zcard(key);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + key + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * 获取zset中元素个数。
     *
     * @param key
     * @param
     * @param
     * @return
     */
    public Long zcard(final byte[] key) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.zcard(key);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + new String(key) + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * @return Long
     * @throws
     * @Title: sadd
     * @Description: set添加元素
     * @author zhengjianian
     * @date 2017年10月11日 下午5:13:46
     */
    public Long sadd(final String key, final String val) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.sadd(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + new String(key) + "]", ex);
                    return null;
                }
            }
        });
    }

    /**
     * @return Long
     * @throws
     * @Title: srem
     * @Description: set删除某个元素
     * @author zhengjianian
     * @date 2017年10月11日 下午5:17:05
     */
    public Long srem(final String key, final String val) {
        return jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.srem(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + new String(key) + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @return Long
     * @throws
     * @Title: sismember
     * @Description: 元素是否存在，存在返回1 不存在返回0
     * @author zhengjianian
     * @date 2017年10月11日 下午5:35:12
     */
    public Boolean sismember(final String key, final String val) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    return shardedJedis.sismember(key, val);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + new String(key) + "]", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @method
     * @description 保存hashmap值
     * @date: 2019/9/1 0001 16:35
     * @author: Niki Zheng
     *@param
     * @return
     */
    public <T extends Serializable> void saveHashMap(final String cacheName, final String field, final T data,
                                                     final int seconds) {
        jedisExecutor.execute(new ShardedJedisCallback<Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                try {
                    final byte[] keyByteArr = String.valueOf(field).getBytes();
                    final byte[] byteArr = SerializationUtils.serialize(data);
                    log.info("cacheName:{} keyByteArr:{} byteArr:{}", cacheName, field, data);
                    Long r = shardedJedis.hset(cacheName.getBytes(), keyByteArr, byteArr);
                    /**
                     * 设置过期时间
                     */
                    if (seconds > 0) {
                        shardedJedis.expire(cacheName, seconds);
                    }
                    return r;

                } catch (Exception ex) {
                    log.error("serialize error.[key:" + cacheName + "],field:" + field + "", ex);
                    return null;
                }
            }
        });
    }

    /**
     * @method
     * @description 根据key 和field 获取值
     * @date: 2019/9/1 0001 16:34
     * @author: Niki Zheng
     *@param
     * @return
     */
    public <T> T getHashMap(final String cacheName, final String field) {
        return jedisExecutor.execute(new ShardedJedisCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T execute(ShardedJedis shardedJedis) {
                try {
                    byte[] prefixByteArr = field.getBytes();
                    byte[] byteArr = shardedJedis.hget(cacheName.getBytes(), prefixByteArr);
                    if (byteArr == null || byteArr.length == 0) {
                        return null;
                    }
                    return (T) SerializationUtils.deserialize(byteArr);
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + cacheName + "],field:" + field + "", ex);
                    return null;
                }
            }
        });
    }


    /**
     * @method
     * @description 根据key 和field删除值
     * @date: 2019/9/1 0001 16:34
     * @author: Niki Zheng
     *@param
     * @return
     */
    public Boolean removeHashMap(final String cacheName, final String field) {
        return jedisExecutor.execute(new ShardedJedisCallback<Boolean>() {
            @Override
            public Boolean execute(ShardedJedis shardedJedis) {
                try {
                    final byte[] prefixByteArr = field.getBytes();
                    Long result = shardedJedis.hdel(cacheName.getBytes(), prefixByteArr);
                    return (result != null && result.longValue() == 1) ? true : false;
                } catch (Exception ex) {
                    log.error("serialize error.[key:" + field + "]", ex);
                    return false;
                }
            }
        });
    }

}
