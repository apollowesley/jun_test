/**
 * 
 */
package net.oschina.j2cache.redis;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月19日
 */
public interface CacheCallback<V> {
	V call();
}
