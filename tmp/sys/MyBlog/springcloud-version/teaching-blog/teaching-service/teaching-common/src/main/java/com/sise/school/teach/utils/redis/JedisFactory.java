package com.sise.school.teach.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author idea
 * @data 2018/11/30
 */
public class JedisFactory {

    private static JedisPool jedisPool;

    private static Jedis jedis;

    private static String URL = "127.0.0.1";

    private static int MAXTOTAL = 50;

    private static int MAXIDLE = 10;

    private static int PORT = 6379;

    private static int TIMEOUT = 1000;

    private static String PASSWORD = "";

    /**
     * 初始化jedis连接池
     */
    public static void initJedisPool() {
        if (jedisPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAXTOTAL);
            config.setMaxIdle(MAXIDLE);
            config.setMaxWaitMillis(TIMEOUT);
            jedisPool = new JedisPool(config, URL, PORT, TIMEOUT);
        }
    }

    public static Jedis getJedisConn(){
        if(jedisPool==null){
            initJedisPool();
        }
        try {
            if(jedis==null){
                synchronized (JedisFactory.class){
                    if(jedis==null){
                        jedis=jedisPool.getResource();
                    }
                }
            }
        }catch (Exception e){
            jedis=jedisPool.getResource();
        }
        return jedis;
    }

}
