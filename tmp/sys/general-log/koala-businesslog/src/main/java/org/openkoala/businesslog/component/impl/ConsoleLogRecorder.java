package org.openkoala.businesslog.component.impl;

import javax.inject.Named;

import org.openkoala.businesslog.component.LogRecorder;

/**
 * 命令行输出日志实现，此为poc使用，真实环境需要做一个数据库输出日志实现。
 * @author xmfang
 *
 */
//@Named
public class ConsoleLogRecorder implements LogRecorder {

	@Override
	public void recordLog(String log) {
		System.out.println("the log is: \n" + log);
	}

}
