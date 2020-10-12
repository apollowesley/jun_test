package com.foo.concurrency;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用BlockingQueue实现生产者消费者问题
 * 本测试用例模仿自BlockingQueue接口官方API上的例子，这个例子没有任何显式的同步，即没有使用synchronized或者Lock，因为队列的阻塞，使得处理过程
 * 被自动地挂起和恢复，BlockingQueue消除了使用显式的wait()和notify()时类与类之间的耦合，每个类只和BlockingQueue通信，更简洁
 *
 * @author Dean
 */
public class ProducerConsumer_QueueTest {
	private AtomicInteger productNo = new AtomicInteger();//产品编码生成器

	/**
	 * 生产者
	 */
	private class Producer implements Runnable {
		private BlockingQueue<String> queue;
		private String productName;
		private int timeSecond; //生产一个产品需要消耗的时间

		Producer(BlockingQueue<String> queue, int timeSecond, String productName) {
			this.queue = queue;
			this.timeSecond = timeSecond;
			this.productName = productName;
		}

		@Override
		public void run() {
			try {
				while (true) {
					produce();
					TimeUnit.SECONDS.sleep(timeSecond);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 生产
		 */
		private void produce() throws InterruptedException {
			String productFullName = productName + "--" + productNo.getAndIncrement();
			queue.put(productFullName);
			System.out.println("生产者" + Thread.currentThread().getName() + "生产了一个产品" + productFullName);
		}
	}

	/**
	 * 消费者
	 */
	private class Consumer implements Runnable {
		private BlockingQueue<String> queue;

		Consumer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				while (true) {
					consume();
					TimeUnit.SECONDS.sleep(3);//可以改变消费速度观察不同的输出结果
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 消费
		 */
		private void consume() throws InterruptedException {
			String product = queue.take();
			System.out.println("消费者" + Thread.currentThread().getName() + "消费了一个产品" + product);
		}
	}

	@Test
	public void test() {
		BlockingQueue<String> queue = new LinkedBlockingDeque<>(10);

		//producer1比producer2生产速度可以不同
		Producer producer1 = new Producer(queue, 1, "iphone7");
		Producer producer2 = new Producer(queue, 2, "小米5s Plus");

		Consumer consumer = new Consumer(queue);

		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(producer1);
		executorService.execute(producer2);
		executorService.execute(consumer);

		try {
			TimeUnit.SECONDS.sleep(60); //比Thread.sleep()代码可读性更高
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}

}
