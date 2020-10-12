package com.niki.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @ProjectName: com-public
 * @Package: com.config
 * @ClassName: RedisConfiguration
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/2/26 14:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Configuration
@Slf4j
public class RedisConfiguration {
    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public ShardedJedisPool redisPoolFactory() throws NoSuchFieldException, IllegalAccessException {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
        /*
         * jedisPoolConfig.setMaxTotal(maxActive);
         * jedisPoolConfig.setMinIdle(minIdle);
         */
        ArrayList<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout());
        if (redisConfig.getPassword() != null && !"".equals(redisConfig.getPassword().trim())) {
            jedisShardInfo.setPassword(redisConfig.getPassword().trim());
        }
        /**
         * 下面这段注释代码是通过反射设置redis数据库，默认使用数据库0，你可以根据自己需求设置成你自己的。最好保持默认
         */
        /*Class<? extends JedisShardInfo> clz = jedisShardInfo.getClass();
        Field declaredField = clz.getDeclaredField("db");
        declaredField.setAccessible(true);
        declaredField.set(jedisShardInfo, 10);*/
        jedisShardInfos.add(jedisShardInfo);
        ShardedJedisPool jedisPool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfos);
        log.info("JedisPool注入成功！redis地址=={}", redisConfig.getHost() + "--prot==" + redisConfig.getPort());
        return jedisPool;
    }


    @Bean
    CacheManager cacheManager(ShardedJedisPool shardedJedisPool) {
        return new CacheManager(shardedJedisPool);
    }


}