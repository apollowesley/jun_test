package com.jeasy.util;

import org.springframework.core.NamedThreadLocal;

/**
 * ThreadLocal 工具类
 * @author taomk
 * @version 1.0
 * @since 15-6-6 上午11:48
 */
public class ThreadLocalUtils {

	private static final NamedThreadLocal<Long> TIME_THREAD_LOCAL = new NamedThreadLocal<>("TIME");

	/**
	 * 存放时间
	 * @param time
	 */
	public static final void putTime(long time) {
		TIME_THREAD_LOCAL.set(time);
	}

	/**
	 * 获取时间
	 */
	public static final long getTime() {
		return TIME_THREAD_LOCAL.get();
	}

	/**
	 * 删除时间
	 */
	public static final void removeTime() {
		TIME_THREAD_LOCAL.remove();
	}
}
