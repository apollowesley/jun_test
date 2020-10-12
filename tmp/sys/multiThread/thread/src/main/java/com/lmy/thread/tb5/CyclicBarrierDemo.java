package com.lmy.thread.tb5;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	Random random=new Random();
	public void meeting(CyclicBarrier cb){
		try {
			Thread.sleep(random.nextInt(4000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" ��������ң��ȴ�����...");
		if(Thread.currentThread().getName().equals("Thread-7")){
//			Thread.currentThread().interrupt();
//			cb.reset();
		}
		try {
			cb.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("���п���");
	}
	public static void main(String[] args) {
		CyclicBarrierDemo d=new CyclicBarrierDemo();
		CyclicBarrier cb=new CyclicBarrier(10, new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("��ʼ����...");
			}
		}));
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					d.meeting(cb);
				}
			}).start();
		}
		System.out.println("���߳�����");
	}
}
