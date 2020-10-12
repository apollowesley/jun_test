package com.freedom.storage.hdfs;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 无业务逻辑的入hdfs
 * 
 * @author zhiqiang.liu 2015年11月13日
 *
 */
public class MyHDFSSupport {

	private static final Log logger = LogFactory.getLog(MyHDFSSupport.class);
	private static ThreadLocal<HashMap<String, MyInformation>> threadLocalInformations = new ThreadLocal<HashMap<String, MyInformation>>() {
		public HashMap<String, MyInformation> initialValue() {
			return new HashMap<String, MyInformation>();
		}
	};

	public static boolean sendFlowEvent(String type, String prefix, String timeFormat, long maxFileSize, String event)
			throws URISyntaxException {
		boolean result = false;// 默认为false
		if (null == type || null == prefix || null == timeFormat || 0 == maxFileSize || null == event) {
			return result;
		}
		// 获取本线程的哈希表
		HashMap<String, MyInformation> map = threadLocalInformations.get();

		// 保证拿到本线程对应的information
		String key = type + prefix + timeFormat + maxFileSize;
		MyInformation information = map.get(key);
		if (null == information) {
			information = new MyInformation(prefix, timeFormat, maxFileSize);
			map.put(key, information);
		}

		// 开始操作
		synchronized (information) {// 所有的逻辑都在这里
			information.checkTimeStrategyEqual();// 1 跨时间策略的文件需要关闭
			information.ensureOpen(); // 2 开启
			result = information.write(event); // 3 写文件
			information.closeForFileSize();// 4 跨指定大小的文件需要关闭
		}
		return result;
	}

	// public static void close() {
	// HashMap<String, MyInformation> myInformations =
	// threadLocalInformations.get();
	// if(myInformations != null) {
	// for(MyInformation information:myInformations.values()) {
	// information.close(false);
	// }
	// }
	// }
}
