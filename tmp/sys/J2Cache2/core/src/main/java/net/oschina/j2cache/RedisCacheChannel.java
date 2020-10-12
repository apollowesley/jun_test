package net.oschina.j2cache;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import net.oschina.j2cache.util.IocUtils;

/**
 * 集成spring-data-redis
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月27日
 */
public class RedisCacheChannel implements CacheExpiredListener, CacheChannel,MessageListener,ApplicationContextAware {

	private final static Logger log = LoggerFactory.getLogger(RedisCacheChannel.class);

	private String channel;
	
	private RedisTemplate<String ,Object> redisTemplate;
	@SuppressWarnings("rawtypes")//
	private RedisSerializer valueSerializer;
	
	/**
	 * 初始化缓存通道并连接
	 * 
	 * @param name
	 *            : 缓存实例名称
	 */
	private RedisCacheChannel(RedisTemplate<String ,Object> redisTemplate) throws CacheException {
		Properties props = J2Cache.getConfig();
		channel = props.getProperty("redis.channel_name","j2cache_channel");
		this.redisTemplate = redisTemplate;
		this.valueSerializer = redisTemplate.getValueSerializer();
	}

	/**
	 * 获取缓存中的数据
	 * 
	 * @param region
	 *            : Cache Region name
	 * @param key
	 *            : Cache key
	 * @return cache object
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String region, Object key) {
		T result = null;
		if (region != null && key != null) {
			result = (T) CacheManager.get(LEVEL_1, region, key);
			if (result == null) {
				result = (T) CacheManager.get(LEVEL_2, region, key);
				if (result != null) {
					CacheManager.set(LEVEL_1, region, key, result);
				}
			}
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param region
	 *            : Cache Region name
	 * @param key
	 *            : Cache key
	 * @param value
	 *            : Cache value
	 */
	public void set(String region, Object key, Object value) {
		if (region != null && key != null) {
			if (value == null)
				evict(region, key);
			else {
				// 分几种情况
				// Object obj1 = CacheManager.get(LEVEL_1, region, key);
				// Object obj2 = CacheManager.get(LEVEL_2, region, key);
				// 1. L1 和 L2 都没有
				// 2. L1 有 L2 没有（这种情况不存在，除非是写 L2 的时候失败
				// 3. L1 没有，L2 有
				// 4. L1 和 L2 都有
				_sendEvictCmd(region, key);// 清除原有的一级缓存的内容
				CacheManager.set(LEVEL_1, region, key, value);
				CacheManager.set(LEVEL_2, region, key, value);
			}
		}
		// log.info("write data to cache region="+region+",key="+key+",value="+value);
	}

	/**
	 * 删除缓存
	 * 
	 * @param region
	 *            : Cache Region name
	 * @param key
	 *            : Cache key
	 */
	public void evict(String region, Object key) {
		CacheManager.evict(LEVEL_1, region, key); // 删除一级缓存
		CacheManager.evict(LEVEL_2, region, key); // 删除二级缓存
		_sendEvictCmd(region, key); // 发送广播
	}

	/**
	 * 批量删除缓存
	 * 
	 * @param region
	 *            : Cache region name
	 * @param keys
	 *            : Cache key
	 */
	@SuppressWarnings({ "rawtypes" })
	public void batchEvict(String region, List keys) {
		CacheManager.batchEvict(LEVEL_1, region, keys);
		CacheManager.batchEvict(LEVEL_2, region, keys);
		_sendEvictCmd(region, keys);
	}

	/**
	 * Clear the cache
	 * 
	 * @param region
	 *            : Cache region name
	 */
	public void clear(String region) throws CacheException {
		CacheManager.clear(LEVEL_1, region);
		CacheManager.clear(LEVEL_2, region);
		_sendClearCmd(region);
	}

	/**
	 * Get cache region keys
	 * 
	 * @param region
	 *            : Cache region name
	 * @return key list
	 */
	@SuppressWarnings("rawtypes")
	public List keys(String region) throws CacheException {
		return CacheManager.keys(LEVEL_2, region);
	}

	/**
	 * 为了保证每个节点缓存的一致，当某个缓存对象因为超时被清除时，应该通知群组其他成员
	 * 
	 * @param region
	 *            : Cache region name
	 * @param key
	 *            : cache key
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void notifyElementExpired(String region, Object key) {

		log.debug("Cache data expired, region=" + region + ",key=" + key);

		// 删除二级缓存
		if (key instanceof List)
			CacheManager.batchEvict(LEVEL_2, region, (List) key);
		else
			CacheManager.evict(LEVEL_2, region, key);

		// 发送广播
		_sendEvictCmd(region, key);
	}

	/**
	 * 发送清除缓存的广播命令
	 * 
	 * @param region
	 *            : Cache region name
	 * @param key
	 *            : cache key
	 */
	private void _sendEvictCmd(String region, Object key) {
		if(!J2Cache.isBcastEnabled())return;
		// 发送广播
		Command cmd = new Command(Command.OPT_DELETE_KEY, region, key);
		redisTemplate.convertAndSend(channel, cmd);
	}

	/**
	 * 发送清除缓存的广播命令
	 * @param region: Cache region name
	 */
	private void _sendClearCmd(String region) {
		if(!J2Cache.isBcastEnabled())return;
		// 发送广播
		Command cmd = new Command(Command.OPT_CLEAR_KEY, region, "");
		redisTemplate.convertAndSend(channel, cmd);
	}

	/**
	 * 删除一级缓存的键对应内容
	 * @param region : Cache region name
	 * @param key  : cache key
	 */
	@SuppressWarnings("rawtypes")
	protected void onDeleteCacheKey(String region, Object key) {
		if (key instanceof List)
			CacheManager.batchEvict(LEVEL_1, region, (List) key);
		else
			CacheManager.evict(LEVEL_1, region, key);
		log.debug("Received cache evict message, region=" + region + ",key=" + key);
	}

	/**
	 * 清除一级缓存的键对应内容
	 * @param region Cache region name
	 */
	protected void onClearCacheKey(String region){
		CacheManager.clear(LEVEL_1, region);
		log.debug("Received cache clear message, region="+region);
	}
	
	/**
	 * 消息接收
	 * 
	 * @param channel 缓存 Channel
	 * @param message 接收到的消息
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		// 无效消息
		if (message != null && message.getBody().length <= 0) {
			log.warn("Message is empty.");
			return;
		}

		try {
			//Command cmd = Command.parse(message.getBody());
			Command cmd = (Command) this.valueSerializer.deserialize(message.getBody());
			
			if (cmd == null || cmd.isLocalCommand())
				return;

			switch (cmd.getOperator()) {
			case Command.OPT_DELETE_KEY:
				onDeleteCacheKey(cmd.getRegion(), cmd.getKey());
				break;
			case Command.OPT_CLEAR_KEY:
				onClearCacheKey(cmd.getRegion());
				break;
			default:
				log.warn("Unknown message type = " + cmd.getOperator());
			}
		} catch (Exception e) {
			log.error("Unable to handle received msg", e);
		}
	}

	/**
	 * 关闭到通道的连接
	 */
	public void close() {
		CacheManager.shutdown(LEVEL_1);
		CacheManager.shutdown(LEVEL_2);
	}


	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		IocUtils.setContext(arg0);
	}

	public void initSpringCacheProvider()  {
		try {
			CacheManager.initCacheProvider(this,redisTemplate);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}


	@Override
	public void batchEvict(String region, Object... keyPatterns) {
		CacheManager.batchEvict(LEVEL_1, region, keyPatterns);
		CacheManager.batchEvict(LEVEL_2, region, keyPatterns);
	}
}
