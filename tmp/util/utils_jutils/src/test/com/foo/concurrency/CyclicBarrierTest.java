package com.foo.concurrency;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 栅栏
 * 允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)，barrier 在释放等待线程后可以重用
 * @author Dean
 */
public class CyclicBarrierTest {
	private int N = 3; //线程数，也是行数
	private int columnNum = 4;//列数
	private int data[][] = new int[N][columnNum];
	private int currentColumn = 0; //当前处理列
	private CyclicBarrier cyclicBarrier;
	private boolean done = false;


	/**
	 * 模拟N个线程并发逐列填充二维数组并计算该列合计，例子模仿自CyclicBarrier JAVA API
	 */
	@Test
	public void test() throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		//CyclicBarrier构造方法支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），
		//该命令只在每个屏障点运行一次
		cyclicBarrier = new CyclicBarrier(N, () -> {
			int total = data[0][currentColumn] + data[1][currentColumn] + data[2][currentColumn];
			System.out.println("第" + currentColumn + "列完成计算，合计=" + total);
			currentColumn++;
			//这里可以控制是否一直计算下去
			if (currentColumn == columnNum) {
				done = true;
			}
		});

		for (int i = 0; i < N; i++) {
			executorService.execute(new Worker(i));
		}

		while(!done){
			TimeUnit.SECONDS.sleep(2);//主线程等待
		}

		System.out.println("打印二维数组");
		for (int i = 0; i < N; i++) {
			System.out.print("[");
			for (int j = 0; j < columnNum; j++) {
				System.out.print(data[i][j] + "-");
			}
			System.out.println("]");
		}
	}

	private class Worker implements Runnable {
		int myRow;

		Worker(int row) {
			myRow = row;
		}

		@Override
		public void run() {
			while (!done) {
				try {
					TimeUnit.SECONDS.sleep(new Random().nextInt(5));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int num = new Random().nextInt(9);
				System.out.println("当前列=" + currentColumn + "，当前行=" + myRow + "，数字=" + num);
				data[myRow][currentColumn] = num;
				try {
					cyclicBarrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
