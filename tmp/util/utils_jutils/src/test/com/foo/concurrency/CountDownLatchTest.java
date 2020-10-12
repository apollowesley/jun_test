package com.foo.concurrency;

import java.util.Random;
import java.util.concurrent.*;

import org.junit.Test;

/**
 * CountDownLatch，被用来同步一个或者多个任务，强制他们等待由其他任务执行的一组操作完成。
 * 向CountDownLatch对象设置一个初始计数值，任何在这个对象上调用await()的方法都将阻塞，直至这个计数值到达0.
 * 其他任务在结束工作时，可以在该对象上调用countDown()来减少这个计数值。CountDownLatch被设计成只能触发一次，计数值不能重置。
 * 如果需要重置计数值，可以用CyclicBarrier. 摘抄自《Think in java 4th》p722
 * CountDownLatch的API上有两种使用场景的范例，本例子与其中一个类似
 *
 * @author Dean
 */
public class CountDownLatchTest {

	private class Worker implements Runnable {
		CountDownLatch startSignal; //启动信号
		CountDownLatch doneSignal; //完成信号

		private Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		@Override
		public void run() {
			try {
				startSignal.await();
				doWork();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				doneSignal.countDown();//完成工作，计数器减一
			}
		}

		private void doWork() throws InterruptedException {
			TimeUnit.SECONDS.sleep(new Random().nextInt(6));//模拟工作花费的时间
			System.out.println("工人" + Thread.currentThread().getName() + "完成工作");
		}
	}


	@Test
	public void test() {
		int workerCount = 3; //工人人数
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(workerCount);

		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < workerCount; i++) {
			executorService.execute(new Worker(startSignal, doneSignal));
		}
		startSignal.countDown();//计数到达0，所有工人同时开工
		try {
			doneSignal.await();//主线程等待直到最后一个工人完工
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("所有工人完成工作");
		executorService.shutdown();
	}
}