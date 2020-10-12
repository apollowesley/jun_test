package org.duomn.naive.common.thread;

public enum TaskEnum {
	/** 普通任务，无并发限制 */
	CONCURRENT_NONE,
	/** 单任务有并发限制*/
	CONCURRENT_INNNER,
	/** 任务之间有资源的并发访问 */
	CONCURRENT_OUTTER
	
}
