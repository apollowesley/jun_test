package com.lmy.thread.t7;
/**
 * ��֤�ɼ��Ե�ǰ��
 * 
 * ����߳��õ�����ͬһ�����������Ǳ�֤���˵ġ�
 * @author Administrator
 *
 */
public class Demo {
	private int i=1;
	public synchronized void setA(int a){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i=a;
	}
	public synchronized int getA(){
		return i;
	}
	public static void main(String[] args) {
		Demo demo=new Demo();
		new Thread(new Runnable(){
			@Override
			public void run() {
				demo.setA(10);
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println(demo.getA());
			}
		}).start();
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("���ս����"+demo.getA());
	}
}
