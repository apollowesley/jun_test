package com.freedom.mysql.myrwsplit.helper;

public class LoggerHelper {
	private org.apache.log4j.Logger logger;

	private LoggerHelper(@SuppressWarnings("rawtypes") Class clazz) {
		logger = org.apache.log4j.LogManager.getLogger(clazz);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String msg) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg);
		}
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(String msg) {
		if (logger.isInfoEnabled()) {
			logger.info(msg);
		}
	}

	public void warn(String msg) {
		// ����ֱ�Ӵ�
		logger.warn(msg);
	}

	public void error(String msg) {
		// ����ֱ�Ӵ�
		logger.error(msg);
	}

	public void fatal(String msg) {
		// ����ֱ�Ӵ�
		logger.fatal(msg);
	}

	public static LoggerHelper getLogger(@SuppressWarnings("rawtypes") Class clazz) {
		return new LoggerHelper(clazz);
	}
}
