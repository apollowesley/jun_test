package com.freedom.storage.hdfs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 无业务逻辑的入hdfs
 * @author zhiqiang.liu
 * 2015年11月13日
 *
 */
public class MonitorQueue {
	// poll: 若队列为空，返回null。
	// remove:若队列为空，抛出NoSuchElementException异常。
	// take:若队列为空，发生阻塞，等待有元素。

	// put---无空间会等待
	// add--- 满时,立即返回,会抛出异常
	// offer---满时,立即返回,不抛异常
	// private static final Logger logger =
	// LoggerFactory.getLogger(MonitorQueue.class);
	public static BlockingQueue<MyInformation> objectQueue = new LinkedBlockingQueue<MyInformation>();

	public static void addObject(MyInformation obj) {
		objectQueue.offer(obj);
	}

	public static MyInformation getObject() {
		return objectQueue.poll();
	}

	static {
		// 一旦有对象加入时，只启动一次检测线程
		Runnable r = new MonitorRunnable();
		new Thread(r).start();
	}

}