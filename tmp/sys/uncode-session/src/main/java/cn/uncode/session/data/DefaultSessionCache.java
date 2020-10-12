package cn.uncode.session.data;

import java.util.List;

import cn.uncode.cache.CacheUtils;

public class DefaultSessionCache implements SessionCache {
	

	@Override
	public void put(String sessionId, SessionMap sessionMap, int sessionTimeOut) {
		CacheUtils.put(sessionId, sessionMap, sessionTimeOut);
	}

	@Override
	public SessionMap get(String sessionId) {
		return (SessionMap) CacheUtils.get(sessionId);
	}

	@Override
	public void setMaxInactiveInterval(String sessionId, int interval) {
		Object object = CacheUtils.get(sessionId);
		CacheUtils.put(sessionId, object, interval);
	}

	@Override
	public void destroy(String sessionId) {
		CacheUtils.remove(sessionId);
	}

	@Override
	public void put(String key, Object value, int timeout) {
		CacheUtils.put(key, value, timeout);

	}

	@Override
	public List<String> getKeys(String pattern) {
		return CacheUtils.getKeys(pattern);
	}

	@Override
	public Object getValue(String key) {
		return CacheUtils.get(key);
	}

}
