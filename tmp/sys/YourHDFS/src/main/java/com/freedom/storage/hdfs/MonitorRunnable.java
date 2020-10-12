package com.freedom.storage.hdfs;

import java.util.ArrayList;

import com.freedom.storage.utils.HDFSProperties;

/**
 * 无业务逻辑的入hdfs
 * 
 * @author zhiqiang.liu 2015年11月13日
 *
 */
public class MonitorRunnable implements Runnable {

	private ArrayList<MyInformation> informations = new ArrayList<MyInformation>();

	public MonitorRunnable() {
	}

	@Override
	public void run() {
		HDFSProperties config = HDFSProperties.getInstance();
		while (true) {
			// 然后再处理MonitorQueue里的对象
			MyInformation myInfor = null;
			while ((myInfor = MonitorQueue.getObject()) != null) {
				// 添加到本地
				informations.add(myInfor);
			}
			// 先遍历每一个ArrayList
			for (MyInformation information : informations) {
				// 尝试获取锁,获取锁后，写句柄是无法写入的
				synchronized (information) {
					information.closeForWriteIdleTime();
				}
			}
			// 睡眠一段时间,防止过度占用写句柄
			try {
				Thread.sleep(config.getCheckFSStreamPeriod());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}