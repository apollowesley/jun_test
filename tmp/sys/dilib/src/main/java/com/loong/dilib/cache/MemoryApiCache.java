package com.loong.dilib.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 简单内存Api缓存处理器
 *
 * @author 张成轩
 */
public class MemoryApiCache extends AbstractApiCache {

	private static Log log = LogFactory.getLog(MemoryApiCache.class);

	private static final int SLEEP = 500;

	private boolean stop;

	private Map<String, String> cacheMap;
	private Map<Method, LinkedList<CT>> timeMap;
	private Thread clearThread;

	/**
	 * 构造方法
	 */
	public MemoryApiCache() {

		stop = false;
		cacheMap = new HashMap<String, String>();
		timeMap = new HashMap<Method, LinkedList<CT>>();

		// 启动内存清理线程
		clearThread = new Thread(new Clearner());
		clearThread.start();
	}

	/**
	 * 关闭
	 */
	public void close() {

		stop = true;
		log.info("[dilib][memory-api-cache] closed");
	}

	@Override
	public String get(Object api, Method method, String key) {

		if (stop)
			throw new RuntimeException("API Cache is closed.");
		return cacheMap.get(key);
	}

	@Override
	public void put(Object api, Method method, String key, String html, int expire) {

		if (stop)
			throw new RuntimeException("API Cache is closed.");

		cacheMap.put(key, html);

		// 加入到时间清理队列
		LinkedList<CT> cts = timeMap.get(method);
		if (cts == null) {
			cts = new LinkedList<CT>();
			timeMap.put(method, cts);
		}
		CT ct = new CT();
		ct.expire = System.currentTimeMillis() + expire * 1000;
		ct.key = key;
		cts.add(ct);
	}

	private class CT {

		private String key;
		private long expire;
	}

	/**
	 * 内存清理器
	 *
	 * @author 张成轩
	 */
	private class Clearner implements Runnable {

		@Override
		public void run() {

			while (true) {
				if (stop)
					return;
				while (true) {
					// 遍历每一个Api方法
					for (LinkedList<CT> cts : timeMap.values()) {
						if (cts.isEmpty())
							break;
						// 获取列头
						CT ct = cts.getFirst();
						if (ct.expire < System.currentTimeMillis()) {
							// 超时移除
							cts.removeFirst();
							cacheMap.remove(ct.key);
						}
					}
					// 跳出等待
					break;
				}
				if (stop)
					return;
				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
