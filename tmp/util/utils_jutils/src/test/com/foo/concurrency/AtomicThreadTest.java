package com.foo.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
 * 测试AtomicInteger的原子性 
 * @author Dean
 */
public class AtomicThreadTest {
	private AtomicInteger atoIntIncreaseNum ; //递增数字AtomicInteger
	private volatile int intIncreaseNum;//volatile不能保证原子性，是否使用volatile对结果没有影响，见《深入理解Java虚拟机》12.3.3

	private int threadCount; //创建的线程数量
	private int loopCount; //附属线程内部循环次数

	private CountDownLatch mainLatch;
	private CountDownLatch subLatch;
	
	@Before
	public void setUp() {
		atoIntIncreaseNum = new AtomicInteger();
		intIncreaseNum = 0;
		threadCount = 100;
		loopCount = 1024;
		mainLatch = new CountDownLatch(1);
		subLatch = new CountDownLatch(threadCount);
	}
	
	@Test
	public void testAtomicIntegerAdd() throws Exception{
		// 创建并启动其他附属线程
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new AtoIntThread(mainLatch, subLatch, loopCount);
			thread.start();
		}
		long beginNano = System.nanoTime();
		// 让其他等待的线程统一开始
		mainLatch.countDown();
		// 等待其他线程执行完
		subLatch.await();
		long endNano = System.nanoTime();
		
		int sum = atoIntIncreaseNum.get();
		
		//预期两者相等
		Assert.assertEquals(sum, threadCount * loopCount);
		
		System.out.println("耗时: " + ((endNano - beginNano) / (1000 * 1000)) + "ms");
		System.out.println("threadCount = " + (threadCount) + ";");
		System.out.println("loopCount = " + (loopCount) + ";");
		System.out.println("sum = " + (sum) + ";");
	}
	
	@Test
	public void testIntAdd() throws Exception{
		// 创建并启动其他附属线程
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new IntThread(mainLatch, subLatch, loopCount);
			thread.start();
		}
		long beginNano = System.nanoTime();
		// 让其他等待的线程统一开始
		mainLatch.countDown();
		// 等待其他线程执行完
		subLatch.await();
		long endNano = System.nanoTime();
		
		int sum = intIncreaseNum;
		
		//预期两者不相等
		Assert.assertNotSame(sum, threadCount * loopCount);
		
		System.out.println("耗时: " + ((endNano - beginNano) / (1000*1000))+ "ms");
		System.out.println("threadCount = " + (threadCount) + ";");
		System.out.println("loopCount = " + (loopCount) + ";");
		System.out.println("sum = " + (sum) + ";");
	}


	private class AtoIntThread  extends Thread {
		private CountDownLatch mainLat = null;
		private CountDownLatch subLat = null;
		private int loopCount;

		private AtoIntThread(CountDownLatch mainLat,
				CountDownLatch subLat, int loopCount) {
			this.mainLat = mainLat;
			this.subLat = subLat;
			this.loopCount = loopCount;
		}

		@Override
		public void run() {
			try {
				this.mainLat.await(); //等待
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < loopCount; i++) {
				atoIntIncreaseNum.getAndIncrement();
			}
			subLat.countDown();
		}
	}

	private class IntThread  extends Thread {
		
		private CountDownLatch mainLat = null;
		private CountDownLatch subLat = null;
		private int loopCount;

		private IntThread(CountDownLatch mainLat,
				CountDownLatch subLat, int loopCount) {
			this.mainLat = mainLat;
			this.subLat = subLat;
			this.loopCount = loopCount;
		}

		@Override
		public void run() {
			try {
				this.mainLat.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < loopCount; i++) {
				intIncreaseNum++;
			}
			subLat.countDown();
		}
	}
}
