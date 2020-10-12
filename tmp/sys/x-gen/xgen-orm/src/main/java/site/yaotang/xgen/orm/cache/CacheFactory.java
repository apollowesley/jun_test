package site.yaotang.xgen.orm.cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 缓存工厂，程序员直接操作的接口
 * @author hyt
 * @date 2018年1月1日
 */
@Data
public class CacheFactory {

	protected static final Map<String, Cache> CACHE_MAP = new HashMap<>();

	static {
		new CacheThread().start();
	}

	/**
	 * 数据放入缓存
	 */
	public static void put(String name, Object value, long seconds) {
		Cache cache = Cache.builder().name(name).value(value).startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusSeconds(seconds)).build();
		CACHE_MAP.put(name, cache);
	}

	/**
	 * 数据从缓存取出
	 */
	public static Object get(String name) {
		Cache cache = CACHE_MAP.get(name);
		if (cache != null && cache.getEndTime().compareTo(LocalDateTime.now()) > 0) {
			return cache.getValue();
		}
		return null;
	}
}