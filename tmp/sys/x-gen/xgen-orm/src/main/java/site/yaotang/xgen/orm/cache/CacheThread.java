package site.yaotang.xgen.orm.cache;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import site.yaotang.xgen.orm.utils.LogUtil;

/**
 * 缓存线程
 * @author hyt
 * @date 2018年1月1日
 */
@Slf4j
public class CacheThread extends Thread {

	@Override
	public void run() {
		String removeName = null;
		Collection<Cache> values = null;
		while (true) {
			removeName = null;
			values = CacheFactory.CACHE_MAP.values();
			for (Cache cache : values) {
				if (cache.isInvalid()) {
					removeName = cache.getName();
					break;
				}
			}
			CacheFactory.CACHE_MAP.remove(removeName);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LogUtil.error(log, e);
				throw new RuntimeException(e);
			}
		}
	}

}
