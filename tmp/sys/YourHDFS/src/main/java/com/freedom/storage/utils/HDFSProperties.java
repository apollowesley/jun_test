package com.freedom.storage.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.apache.log4j.LogManager;

/**
 * 
 * @author zhiqiang.liu
 * @2016年1月1日
 *
 */

public class HDFSProperties {
	// 以下为全局需要
	private static final Logger logger = LogManager.getLogger(HDFSProperties.class);

	// 测试
	public static void main(String[] args) {
		// just for test
		HDFSProperties property = HDFSProperties.getInstance();
		LoggerUtils.debug(logger, property.toString());
	}

	public static HDFSProperties getInstance() {
		return myProperties;
	}

	//
	private static HDFSProperties myProperties = null;// 全局单例变量，一开始就存在
	static {// 静态块里，只加载一次

		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(HDFS_CONFIG.HDFS_CONFIG_FILE));
			// Thread.currentThread().getContextClassLoader().getResourceAsStream(MyConstants.CONFIG_FILE);
			props.load(in);
			in.close();
		} catch (Exception e) {
			// logger.error(e.toString());
			LoggerUtils.error(logger, "fail to read config file " + HDFS_CONFIG.HDFS_CONFIG_FILE);
			System.exit(-1);
		}
		// 读取值
		LoggerUtils.debug(logger, "succeed to read config file " + HDFS_CONFIG.HDFS_CONFIG_FILE);
		//
		String scheme = props.getProperty(HDFS_CONFIG.HDFS_SCHEME);
		String ip0 = props.getProperty(HDFS_CONFIG.HDFS_IP0);
		String ip1 = props.getProperty(HDFS_CONFIG.HDFS_IP1);
		int port = Integer.parseInt(props.getProperty(HDFS_CONFIG.HDFS_PORT));
		int operationTimeSpan = Integer.parseInt(props.getProperty(HDFS_CONFIG.HDFS_TIME_SPAN));
		int checkFSStreamPeriod = Integer.parseInt(props.getProperty(HDFS_CONFIG.HDFS_CHECK_PERIOD));
		props.clear();
		props = null;
		// 构造新的对象
		myProperties = new HDFSProperties(scheme, ip0, ip1, port, operationTimeSpan, checkFSStreamPeriod);
		LoggerUtils.info(logger, "succeed to create my properties object ");
		LoggerUtils.info(logger, "cpu - " + Runtime.getRuntime().availableProcessors());
	}

	// 私有属性开始//////////////////////////////////////////////////////////////////

	private String scheme;
	private String ip0;
	private String ip1;
	private int port;
	private int ioTimeSpan;
	private int checkFSStreamPeriod;

	private HDFSProperties() {// 私有方法，保证单例
	}

	private HDFSProperties(String _scheme, String _ip0, String _ip1, int _port, //
			int _ioTimeSpan, int _checkFSStreamSpan) {
		//
		this.scheme = _scheme;
		this.ip0 = _ip0;
		this.ip1 = _ip1;
		this.port = _port;
		this.ioTimeSpan = _ioTimeSpan;
		this.checkFSStreamPeriod = _checkFSStreamSpan;
	}

	public int getPort() {
		return port;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getIp0() {
		return ip0;
	}

	public void setIp0(String ip0) {
		this.ip0 = ip0;
	}

	public String getIp1() {
		return ip1;
	}

	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	public int getIOTimeSpan() {
		return ioTimeSpan;
	}

	public int getCheckFSStreamPeriod() {
		return checkFSStreamPeriod;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append(HDFS_CONFIG.HDFS_SCHEME).append(": ").append(this.scheme).append(" ");
		strBuilder.append(HDFS_CONFIG.HDFS_IP0).append(": ").append(this.ip0).append(" ");
		strBuilder.append(HDFS_CONFIG.HDFS_IP1).append(": ").append(this.ip1).append(" ");
		strBuilder.append(HDFS_CONFIG.HDFS_PORT).append(": ").append(this.port).append(" ");
		strBuilder.append(HDFS_CONFIG.HDFS_TIME_SPAN).append(": ").append(this.ioTimeSpan).append(" ");
		strBuilder.append(HDFS_CONFIG.HDFS_CHECK_PERIOD).append(": ").append(this.checkFSStreamPeriod).append(" ");
		return strBuilder.toString();
	}

	// 内部类
	static class HDFS_CONFIG {
		public static String HDFS_CONFIG_FILE = System.getProperty("hdfsProperties",
				"src/main/resources/hdfs.properties");
		public static String HDFS_SCHEME = "hdfs_scheme";
		public static String HDFS_IP0 = "hdfs_ip0";
		public static String HDFS_IP1 = "hdfs_ip1";
		public static String HDFS_PORT = "hdfs_port";
		public static String HDFS_TIME_SPAN = "hdfs_file_operation_time_span";
		public static String HDFS_CHECK_PERIOD = "hdfs_check_fsstream_period";
	}
}
