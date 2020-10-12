/**
 * 
 */
package net.oschina.j2cache.redis;

import org.springframework.data.redis.listener.ChannelTopic;

import net.oschina.j2cache.J2Cache;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月28日
 */
public class CacheNotifyTopic extends ChannelTopic{

	/**
	 * @param name
	 */
	public CacheNotifyTopic() {
		super(J2Cache.getConfig().getProperty("redis.channel_name","j2cache_channel"));
	}

}
