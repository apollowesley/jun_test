package com.lmy.thread.ta8;

public class Demo {
	private int signal;
	public synchronized void a(){
		while(signal!=0){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("a"+signal);
		signal++;
		notifyAll();
	}
	public synchronized void b(){
		while(signal!=1){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("b"+signal);
		signal++;
		notifyAll();
	}
	public synchronized void c(){
		while(signal!=2){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("c"+signal);
		signal=0;
		notifyAll();
	}
	
	public static void main(String[] args) {
		Demo d=new Demo();
		A a=new A(d);
		B b=new B(d);
		C c=new C(d);
		
		new Thread(a).start();
		new Thread(b).start();
		new Thread(c).start();
	}
}
