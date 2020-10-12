//package com.slavic.veles.utils;
//
//import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
////@Configuration
////@PropertySource("")
//public class RedisConfig {
//	@Value("${redis.host}")
//	private String host;
//
//	@Value("${redis.port}")
//	private int port;
//
//	@Value("${redis.timeout}")
//	private int timeout;
//
//	@Value("${redis.auth}")
//	private String auth;
//
//	@Value("${redis.database}")
//	private int database;
//
//	@Value("${redis.maxTotal}")
//	private int maxTotal;
//
//	@Value("${redis.maxIdle}")
//	private int maxIdle;
//
//	@Value("${redis.minIdle}")
//	private int minIdle;
//
//	@Value("${redis.maxWaitMillis}")
//	private int maxWaitMillis;
//
//	@Bean
//	@Primary
//	public RedisTemplate<String, Object> redisTemplate() {
//		//基本配置
//		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//		configuration.setHostName(host);
//		configuration.setPort(port);
//		configuration.setDatabase(database);
//		RedisPassword redisPassword = RedisPassword.of(auth);
//		configuration.setPassword(redisPassword);
//
//		/* ========= 连接池通用配置 ========= */
//		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//		genericObjectPoolConfig.setMaxTotal(maxTotal);
//		genericObjectPoolConfig.setMinIdle(minIdle);
//		genericObjectPoolConfig.setMaxIdle(maxIdle);
//		genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
//		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration,
//				LettucePoolingClientConfiguration.builder()
//						.poolConfig(genericObjectPoolConfig)
//						.commandTimeout(Duration.ofSeconds(timeout)).build());
//		{//配置redis客户端(https://blog.csdn.net/catoop/article/details/93756295)
//			connectionFactory.afterPropertiesSet();
//		}
//		//创建redisTemplate
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(connectionFactory);
//		{//设置序列化器-可以改为使用fastJson进行序列化(https://my.oschina.net/u/3777515/blog/1813730/)
//			FastJsonRedisSerializer<Object> redisSerializer = new FastJsonRedisSerializer<>(Object.class);
//			redisTemplate.setHashValueSerializer(redisSerializer);
//			redisTemplate.setValueSerializer(redisSerializer);
//		}
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.afterPropertiesSet();
//		return redisTemplate;
//	}
//}
