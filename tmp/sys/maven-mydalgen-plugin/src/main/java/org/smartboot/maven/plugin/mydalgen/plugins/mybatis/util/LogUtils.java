/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 日志工具
 * 
 * @author seer
 * @version LogUtils.java, v 0.1 2017年1月3日 下午3:40:33 Seer Exp.
 */
public final class LogUtils {
	private static final Logger _log = Logger.getLogger("LOGGER");
	private static Formatter formatter = new Formatter() {
		private final String format = "[%1$s] %2$s%3$s%n";

		@Override
		public String format(LogRecord record) {
			String message = formatMessage(record);
			String throwable = "";
			if (record.getThrown() != null) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				pw.println();
				record.getThrown().printStackTrace(pw);
				pw.close();
				throwable = sw.toString();
			}
			return String.format(Locale.ENGLISH, format, record.getLevel().getName(), message, throwable);
		}
	};

	static {
		Handler handler = new ConsoleHandler();
		handler.setFormatter(formatter);
		_log.setUseParentHandlers(false);
		_log.addHandler(handler);
	}

	public static final Logger get() {
		return _log;
	}

	public static final void log(String msg) {
		_log.info(msg);
	}

	public static final void error(String msg, Throwable e) {
		_log.log(Level.SEVERE, msg, e);
	}

	public static final void waring(String msg) {
		_log.log(Level.WARNING, msg);
	}

	public static void main(String[] args) {
		LogUtils.log("ad");
	}
}
