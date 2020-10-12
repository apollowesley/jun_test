/**
 * 
 */
package net.oschina.j2cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import net.oschina.j2cache.Cache;
import net.oschina.j2cache.CacheConst;
import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.util.CacheKeyUtils;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月26日
 */
@SuppressWarnings("unchecked")
public class RedisCache implements Cache {
	
	private final static Logger log = LoggerFactory.getLogger(RedisCache.class);
	
	private String region;
	private String regionKeysKey;
	private byte[] regionKeysKeyByte;
	private long expireTimeSeconds = 86400;//单位秒
	private RedisTemplate<String ,Object> redisTemplate;
	
	@SuppressWarnings("rawtypes")//
	private RedisSerializer keySerializer;
	@SuppressWarnings("rawtypes")//
	private RedisSerializer valueSerializer;
	
	//计算关联key集合权重的基数
	private long baseScoreInRegionKeysSet = System.currentTimeMillis()/1000;
	
	public RedisCache(String region, RedisTemplate<String, Object> redisTemplate) {
		this.expireTimeSeconds = Long.parseLong(J2Cache.getConfig().getProperty("cache.L2.default.expire", "86400"));
		this.region = region;
		this.regionKeysKey = this.region + CacheConst.GROUPKEY_SUFFIX;
		this.redisTemplate = redisTemplate;
		this.keySerializer = redisTemplate.getKeySerializer();
		this.valueSerializer = redisTemplate.getValueSerializer();
		this.regionKeysKeyByte = this.keySerializer.serialize(this.regionKeysKey);
	}

	@Override
	public Object get(Object key) throws CacheException {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void put(final Object key, final Object value) throws CacheException {
	
		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				setKeyAndUpdateRalateKeys(key, value, connection);
				return null;
			}
		});
	}
	
	/**
	 * @param key
	 * @param value
	 * @param connection
	 */
	private void setKeyAndUpdateRalateKeys(final Object key, final Object value, RedisConnection connection) {
		//1.设置缓存
		byte[] keyBytes = keySerializer.serialize(key);
		connection.set(keyBytes, valueSerializer.serialize(value));
		//2.设置过期时间
		connection.expire(keyBytes, expireTimeSeconds);
		//3.更新该region下key集合
		//非按主键查询的把key放在缓存组里面
		if(key.toString().indexOf(CacheConst.PK_KEY_JOIN_STR) <= 0){					
			//3.更新该region下key集合
			long score = calcScoreInRegionKeysSet();
			connection.zAdd(regionKeysKeyByte, score, valueSerializer.serialize(key));
			connection.expire(regionKeysKeyByte, expireTimeSeconds);
			log.debug(">add new key[{}] to RegionKeys,score:{}",key,score);
		}
	}
	
	/**
	 * 避免关联key集合越积越多，按插入的先后顺序计算score便于后续定期删除。<br>
	 * Score 即为 实际过期时间的时间戳
	 * @return
	 */
	private long calcScoreInRegionKeysSet(){
		long currentTime = System.currentTimeMillis()/1000;
		long score = currentTime + this.expireTimeSeconds - this.baseScoreInRegionKeysSet;
		return score;
	}

	@Override
	public void update(Object key, Object value) throws CacheException {
		put(key, value);
	}

	@Override
	public List keys() throws CacheException {
		Set<Object> keys = redisTemplate.opsForZSet().range(regionKeysKey, 0, -1);
		return keys == null ? null : new ArrayList<>(keys);
	}

	@Override
	public void evict(Object key) throws CacheException {
		redisTemplate.delete(key.toString());
		redisTemplate.opsForZSet().remove(regionKeysKey, key);
	}

	@Override
	public void batchEvict(List keys) throws CacheException {
		redisTemplate.delete(keys);
		redisTemplate.opsForZSet().remove(regionKeysKey, keys.toArray());
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				//删除关联组的缓存
				Set<byte[]> keysInRegion = connection.zRange(regionKeysKeyByte, 0, -1);
				if(keysInRegion.size() > 0){
					byte[][] keyArray = keysInRegion.toArray(new byte[0][0]);
					connection.zRem(regionKeysKeyByte, keyArray);
					//先转成key的序列化格式
					for (int i = 0; i < keyArray.length; i++) {
						keyArray[i] = keySerializer.serialize(valueSerializer.deserialize(keyArray[i]));
					}
					connection.del(keyArray);
					log.debug("cascade remove cache keyPattern:{},size:{}",regionKeysKey,keysInRegion.size());
				}
				//删除ID的缓存
				String idKeyPattern = region + CacheConst.PK_KEY_JOIN_STR + "*";
				Set<byte[]> idKeys = connection.keys(idKeyPattern.getBytes());
				if(idKeys.size() > 0){					
					connection.del(idKeys.toArray(new byte[0][0]));
					log.debug("cascade remove cache keyPattern:{},size:{}",idKeyPattern,idKeys.size());
				}
				return null;
			}
		});
		
	}
	
	/**
	 * 清除同一cacheName的key集合中过期key
	 */
	public void clearExpiredRegionKeys(){
		long maxScore = System.currentTimeMillis()/1000 - this.baseScoreInRegionKeysSet;
		redisTemplate.opsForZSet().removeRangeByScore(regionKeysKey, 0, maxScore);
		log.info("ClearExpiredRegionKeysTimer runing:cacheName:{} , score range:0~{}",region,maxScore);
	}

	@Override
	public void destroy() throws CacheException {}


	@Override
	public void batchEvict(Object... keyPatterns) throws CacheException {
		if(keyPatterns == null || keyPatterns.length == 0)return;
		if(keyPatterns.length == 1 && !keyPatterns[0].toString().endsWith(CacheKeyUtils.KEY_WILDCARD)){
			evict(keyPatterns[0]);
			return;
		}
		
		List keys = new ArrayList<>();
		for (Object key : keyPatterns) {
			if(key.toString().endsWith(CacheKeyUtils.KEY_WILDCARD)){
				keys.add(redisTemplate.keys(key.toString()));
			}else{
				keys.add(key);
			}
		}
		
		batchEvict(keys);
	}

}
