package cn.uncode.session.data.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.uncode.session.data.ByteUtil;
import cn.uncode.session.data.SerializeUtil;
import cn.uncode.session.data.SessionCache;
import cn.uncode.session.data.SessionMap;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisClusterSessionCache implements SessionCache {

	private static final Logger LOG = LoggerFactory.getLogger(JedisClusterSessionCache.class);

	private JedisClusterCustom jedisCluster;

	public JedisClusterCustom getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisClusterCustom jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public void put(String sessionId, SessionMap sessionMap, int sessionTimeOut) {
		byte[] tkey = SerializeUtil.serialize(sessionId);
		try {
			jedisCluster.set(tkey, SerializeUtil.serialize(sessionMap));
			jedisCluster.expire(tkey, sessionTimeOut);
		} catch (Exception e) {
			LOG.error("[putx] redis cache error", e);
		} finally {
		}
	}

	@Override
	public SessionMap get(String sessionId) {
		byte[] reslut = null;
		byte[] tkey = SerializeUtil.serialize(sessionId);
		try {
			if (jedisCluster.exists(tkey)) {
				reslut = jedisCluster.get(tkey);
				SessionMap object = (SessionMap) SerializeUtil
						.unserialize(reslut);
				return object;
			} else {
				return null;
			}
		} catch (Exception e) {
			LOG.error("[get] redis cache error", e);
			return null;
		} finally {
		}
	}

	@Override
	public void setMaxInactiveInterval(String sessionId, int interval) {
		byte[] tkey = SerializeUtil.serialize(sessionId);
		try {
			if (jedisCluster.exists(tkey)) {
				jedisCluster.expire(tkey, interval);
			}
		} catch (Exception e) {
			LOG.error("[setMaxInactiveInterval] redis cache error", e);
		}
	}

	@Override
	public void destroy(String sessionId) {
		byte[] tkey = SerializeUtil.serialize(sessionId);
		try {
			if (jedisCluster.exists(tkey)) {
				jedisCluster.expire(tkey, 0);
			}
		} catch (Exception e) {
			LOG.error("Destroy session from redis error", e);
		} finally {
		}
	}

	@Override
	public void put(String sessionId, Object value, int timeout) {
		try {
			byte[] tkey = SerializeUtil.serialize(sessionId);
			jedisCluster.set(tkey, SerializeUtil.serialize(value));
			jedisCluster.expire(tkey, timeout);
		} catch (Exception e) {
			LOG.error("Put to redis error", e);
		} finally {
		}
	}

	@Override
	public List<String> getKeys(String pattern) {
		try {
			List<String> list = new ArrayList<String>();
			TreeSet<String> keys = keys(pattern);
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
		byte[] reslut = null;
		byte[] tkey = SerializeUtil.serialize(key);
		try {
			if (jedisCluster.exists(tkey)) {
				reslut = jedisCluster.get(tkey);
				Object object = SerializeUtil.unserialize(reslut);
				return object;
			} else {
				return null;
			}
		} catch (Exception e) {
			LOG.error("[getValue] redis cache error", e);
			return null;
		} finally {
		}
	}
	
	
	private TreeSet<String> keys(String pattern) {
		TreeSet<String> keys = new TreeSet<String>();
		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
		for (String key : clusterNodes.keySet()) {
			JedisPool jp = clusterNodes.get(key);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connection.close();
			}
		}
		return keys;
	}
	
}
