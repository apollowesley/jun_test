//package com.slavic.veles.utils;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//
////@Component
//public class CacheUtils {
//
//	private final RedisTemplate<String, Object> redisTemplate;
//
//	public CacheUtils(RedisTemplate<String, Object> redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
//
//	public <T> boolean setex(String key, T value, Duration timeout) {
//		try {
//			redisTemplate.opsForValue().set(key, value, timeout);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	public <T> boolean set(String key, String value) {
//		try {
//			redisTemplate.opsForValue().set(key, value);
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public <T> T get(String key) {
//		return key == null ? null : (T) redisTemplate.opsForValue().get(key);
//	}
//}
