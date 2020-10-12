package com.freedom.storage.hdfs;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.freedom.storage.utils.HDFSProperties;

/**
 * 无业务逻辑的入hdfs
 * 
 * @author zhiqiang.liu 2015年11月13日
 *
 */
public class MyInformation {

	private static final Log logger = LogFactory.getLog(MyInformation.class);
	private static Configuration conf = null;
	private static String YEAR = "/yyyy/";
	private static String MONTH = "/yyyy/MM/";
	private static String DAY = "/yyyy/MM/dd/";
	private static String HOUR = "/yyyy/MM/dd/HH/";
	private static String MINUTE = "/yyyy/MM/dd/HH/mm/";
	private static String SECOND = "/yyyy/MM/dd/HH/mm/ss/";

	static {
		conf = new Configuration();
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
	}

	public static final ThreadLocal<MyHashMap> TIME_FORMAT = new ThreadLocal<MyHashMap>() {
		protected MyHashMap initialValue() {
			MyHashMap map = new MyHashMap();
			map.put(YEAR, new MyDateFormat(YEAR));
			map.put(MONTH, new MyDateFormat(MONTH));
			map.put(DAY, new MyDateFormat(DAY));
			map.put(HOUR, new MyDateFormat(HOUR));
			map.put(MINUTE, new MyDateFormat(MINUTE));
			map.put(SECOND, new MyDateFormat(SECOND));
			return map;
		}

	};

	// 老的原始数据
	private String timeFormat;
	private String prefix;
	private long maxFileSize;
	// 后来产生的数据
	private String hdfsMasterIP;
	private String hdfsBackIP;

	private FSDataOutputStream hdfsStream;
	private String streamCreateTime;// 记录上面文件流写文件的小时
	private long hdfsFileSize;

	private long lastWriteTime;

	// 当前统计信息
	private long writeCount = 0;

	public MyInformation(String p, String tf, long s) {
		// 老的原始数据
		timeFormat = tf;
		prefix = p;
		maxFileSize = s;
		// 后来产生的数据
		hdfsMasterIP = HDFSProperties.getInstance().getIp0();
		hdfsBackIP = HDFSProperties.getInstance().getIp1();
		hdfsStream = null;
		streamCreateTime = null;
		hdfsFileSize = 0;
		lastWriteTime = 0;
		// 初始化时增加进入超时监控队列
		MonitorQueue.addObject(this);
	}

	public void checkTimeStrategyEqual() {
		if (null == this.hdfsStream || null == this.streamCreateTime)
			return;// 在合理的前提下

		boolean timeEqual = TIME_FORMAT.get().get(this.timeFormat).format(new Date()).equals(this.streamCreateTime);
		if (!timeEqual) {// 不是同一个时间就关闭流
			this.close(false);
		}
	}

	public void ensureOpen() {
		// 在合理的前提下
		if (null != this.hdfsStream)
			return;

		// 尝试打开
		HDFSProperties config = HDFSProperties.getInstance();
		final String dateStr = TIME_FORMAT.get().get(this.timeFormat).format(new Date());
		String path = this.prefix + dateStr + UUID.randomUUID().toString();
		FileSystem hdfsFileSystem = null;
		FSDataOutputStream fsStream = null;
		try {
			URI masterUri = new URI(config.getScheme() + this.hdfsMasterIP + ":" + config.getPort());
			hdfsFileSystem = FileSystem.get(masterUri, conf);
			fsStream = hdfsFileSystem.create(new Path(path), true);
		} catch (Exception e) {
			logger.error("通过master ip 获取 file system 失败", e);
			try {
				URI backupUri = new URI(config.getScheme() + this.hdfsBackIP + ":" + config.getPort());
				hdfsFileSystem = FileSystem.get(backupUri, conf);
				fsStream = hdfsFileSystem.create(new Path(path), true);
				this.exchangeIP();
			} catch (Exception e1) {
				logger.error("通过slave ip 获取 file system 失败" + e1.toString());
			}
		}
		// 保留结果
		if (null != fsStream) {
			this.hdfsStream = fsStream;
			this.streamCreateTime = dateStr;
			this.hdfsFileSize = 0;
			this.lastWriteTime = System.currentTimeMillis();
		}

	}

	public boolean write(String event) {
		if (null == this.hdfsStream) // 在合理的条件下
			return false;
		try {// 写文件
			byte[] data = event.getBytes("UTF-8");
			this.hdfsStream.write(data, 0, data.length);
			this.hdfsFileSize += event.length();
			this.lastWriteTime = System.currentTimeMillis();
			writeCount++;
			return true;
		} catch (Exception e) {
			logger.error("" + e.toString());
			this.close(true);
			return false;
		}

	}

	public void closeForFileSize() {
		if (null == this.hdfsStream) {// 在必要的前提下
			return;
		}
		// 必要时关闭文件，更新内容大小
		if (this.hdfsFileSize >= this.maxFileSize) {
			this.close(false);
		}
	}

	public void closeForWriteIdleTime() {
		if (null == this.hdfsStream) {
			return;
		}
		HDFSProperties config = HDFSProperties.getInstance();
		if (System.currentTimeMillis() - this.lastWriteTime >= config.getIOTimeSpan()) {
			this.close(false);
		}
	}

	public void close(boolean exception) {
		// 重新设置stream
		logger.debug("MyInformation.close() is invoked..." + (exception ? "写异常发生" : "") + " fileSize:" + hdfsFileSize
				+ " byte " + " writeCount:" + this.writeCount);
		if (null != hdfsStream) {
			try {
				hdfsStream.hflush();
			} catch (Exception e) {
				logger.error("", e);
			}
			try {
				hdfsStream.close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		// 只全部复原后来产生的老数据,老的数据不用更新，否则也不会找到这个information
		hdfsStream = null;
		streamCreateTime = null;
		hdfsFileSize = (long) 0;
		lastWriteTime = 0;
		writeCount = 0;
	}

	public void exchangeIP() {
		String oldMaster = this.hdfsMasterIP;
		String oldBackUp = this.hdfsBackIP;
		this.hdfsMasterIP = oldBackUp;
		this.hdfsBackIP = oldMaster;
	}

}
