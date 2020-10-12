package cn.uncode.session.data.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uncode.session.data.ByteUtil;
import cn.uncode.session.data.SerializeUtil;
import cn.uncode.session.data.SessionCache;
import cn.uncode.session.data.SessionMap;
import redis.clients.jedis.Jedis;

public class RedisSessionCache implements SessionCache{
	
	private static final Logger LOG =LoggerFactory.getLogger(RedisSessionCache.class);
	
	private RedisSentinelPool cachePool;
	
	@Override
	public void put(String sessionId, SessionMap sessionMap, int timeout) {
		Jedis jedis = null;
		try {
			jedis = cachePool.getResource();
            jedis.set(sessionId.getBytes(), SerializeUtil.serialize(sessionMap));
            jedis.expire(sessionId, timeout);
		} catch (Exception e) {
			LOG.error("Put session to redis error", e);
		} finally {
			jedis.close();
		}
	}
	
	@Override
	public void put(String key, Object value, int timeout) {
		Jedis jedis = null;
		try {
			jedis = cachePool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key, timeout);
		} catch (Exception e) {
			LOG.error("Put key to redis error", e);
		} finally {
			jedis.close();
		}
	}

	@Override
	public List<String> getKeys(String pattern) {
		Jedis jedis = null;
		try {
			List<String> list = new ArrayList<String>();
			jedis = cachePool.getResource();
			Set<String> keys = jedis.keys(pattern);
			for (String str : keys) {
				Object obj = SerializeUtil.unserialize(ByteUtil.stringToByte(str));
				if (obj != null) {
					list.add(obj.toString());
				}
			}
			return list;
		} catch (Exception ex) {
			LOG.error("getKeys error", ex);
			return null;
		}
	}

	@Override
	public Object getValue(String key) {
		Jedis jedis = null;
		byte[] reslut = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(key)) {
                reslut = jedis.get(key.getBytes());
                return SerializeUtil.unserialize(reslut);
            }
        } catch (Exception e) {
        	LOG.error("Read session from redis error", e);
            return null;
        } finally {
        	jedis.close();
        }
        return null;
	}

	@Override
	public SessionMap get(String sessionId) {
		Jedis jedis = null;
		SessionMap sessionMap = null;
		byte[] reslut = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(sessionId)) {
                reslut = jedis.get(sessionId.getBytes());
                sessionMap = (SessionMap) SerializeUtil.unserialize(reslut);
            }
        } catch (Exception e) {
        	LOG.error("Read session from redis error", e);
            return null;
        } finally {
        	jedis.close();
        }
        return sessionMap;
	}

	@Override
	public void setMaxInactiveInterval(String sessionId, int interval) {
		Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(sessionId)) {
            	jedis.expire(sessionId, interval);
            }
        } catch (Exception e) {
        	LOG.error("Set session max inactive interval to redis error", e);
        } finally {
        	jedis.close();
        }
	}

	@Override
	public void destroy(String sessionId) {
		Jedis jedis = null;
        try {
            jedis = cachePool.getResource();
            if (jedis.exists(sessionId)) {
            	jedis.expire(sessionId, 0);
            }
        } catch (Exception e) {
        	LOG.error("Destroy session from redis error", e);
        } finally {
        	jedis.close();
        }

	}

	public void setCachePool(RedisSentinelPool cachePool) {
		this.cachePool = cachePool;
	}


	

}
