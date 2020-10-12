package cn.uncode.session.data;

import java.util.List;

/**
 * 
 * @author yeweijun
 */
public interface SessionCache {
	
	/**
	 * 存储session到分布式缓存
	 * @param sessionId 当前会话id
	 * @param sessionMap 值对象
	 * @param timeout 过期时间
	 */
	public void put(String sessionId, SessionMap sessionMap, int sessionTimeOut );
	
	
	/**
	 * 从分布式缓存获取会话
	 * @param sessionId 当前会话id
	 * @return 会话对象
	 */
	public SessionMap get(String sessionId);
	
	
	/**
	 * 设置会话有效时间
	 * @param sessionId 当前会话id
	 * @param interval 有效时间，单位秒
	 */
	public void setMaxInactiveInterval(String sessionId, int interval);
	
	
	/**
	 * 销毁当前会话
	 * @param sessionId 当前会话id
	 */
	public void destroy(String sessionId);
	
	/**
	 * 保存信息
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public void put(String key, Object value, int timeout);
	
	/**
	 * 获取列表信息
	 * @param pattern
	 * @return
	 */
	public List<String> getKeys(String pattern);
	/**
	 * 根据key拿到值
	 * @param key
	 * @return
	 * @author Jason.Li
	 * @date 2016年4月25日上午11:20:32
	 */
	public Object getValue(String key);
	

}
