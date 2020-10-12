package org.duomn.naive.common.thread;

/**
 * 任务类，使用线程池调度任务时，重写initTask来初始化任务的属性，达到关联数据库，记录任务运行情况的目的
 * @author Hu Qiang
 *
 */
public abstract class Task implements Runnable {
	/** 任务的UUID，与数据库关联时使用 */
	private String taskId;
	
	private String taskName;
	
	private TaskEnum taskType = TaskEnum.CONCURRENT_NONE;
	
	protected void initTask() {}
	
	public final String getTaskId() {
		return taskId;
	}

	protected final void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public final String getTaskName() {
		return taskName;
	}

	protected final void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public final TaskEnum getTaskType() {
		return taskType;
	}

	protected final void setTaskType(TaskEnum taskType) {
		this.taskType = taskType;
	}
	
}
