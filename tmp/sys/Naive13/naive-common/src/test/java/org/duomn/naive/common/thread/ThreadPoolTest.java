package org.duomn.naive.common.thread;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.duomn.naive.common.thread.ThreadPool.ThreadDiagnose;
import org.duomn.naive.common.util.BF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ThreadPoolTest {

	@Test
	public void testPool() {
		final ThreadPool pool = BF.getThreadPool();
		
		MyTask[] tasks = new MyTask[10];
		for (int i = 0; i < 10; i++) {
			tasks[i] = new MyTask(i);
		}
		
		ExecutorService invokePool = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 10; i++) {
			final Task task = tasks[i];
			invokePool.execute(new Runnable() {
				public void run() {
					pool.execute(task);
				}
			});
		}
		
		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		ThreadDiagnose[] diagnoses = pool.getDiagnose();
		while (pool.getDiagnose().length != 0) {
			StringBuffer buf = new StringBuffer();
			for (ThreadDiagnose diag : diagnoses) {
				buf.append(String.format("%-15s", "TaskName")).append("\t").append(String.format("%-15s", "CostTime")).append("\n")
				.append(String.format("%-15s",diag.task.getTaskName())).append("\t")
				.append(String.format("%-15d",System.currentTimeMillis() - diag.startTime)).append("\n");
			}
			System.out.println(buf.toString());
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			diagnoses = pool.getDiagnose();
		}
		
	}
	
	public static class MyTask extends Task {
		private int i;
		
		public MyTask(int i) {
			this.i = i;
		}
		
		protected void initTask() {
			String taskId = UUID.randomUUID().toString();
			String taskName = "task" + i;
			setTaskId(taskId);
			setTaskName(taskName);
			setTaskType(TaskEnum.CONCURRENT_NONE);
		}
		
		public void run() {
			System.out.println("I am " + getTaskName() + ", my id is " + getTaskId() + ", I well sleep.");
			Random r = new Random();
			try {
				Thread.sleep(r.nextInt(5) * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("I am " + getTaskName() + ", my id is " + getTaskId() + ", sleep complete end.");
		}
	}
}
