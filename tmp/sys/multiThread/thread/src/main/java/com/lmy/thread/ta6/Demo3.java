package com.lmy.thread.ta6;

public class Demo3 {
	private volatile int singal;

	public synchronized void set() {
		singal=1;
		notify();
		System.out.println("�����߳̽���֮�����߿�ʼ...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized int get() {
		System.out.println(Thread.currentThread().getName()+"����ִ����...");
		if(singal!=1){
			try {
				wait();//wait֮�� ���ͷŵ�synchronized����
				System.out.println("����֮��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+"����ִ�����...");
		return this.singal;
	}
	
	public static void main(String[] args) {
		Demo3 d=new Demo3();
		Target1 t1=new Target1(d);
		Target2 t2=new Target2(d);
		
		new Thread(t2).start();
		new Thread(t2).start();
		new Thread(t2).start();
		new Thread(t2).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(t1).start();
	}
}
