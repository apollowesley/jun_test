package com.lmy.thread.t3;

public class Sequence {
	private static int i;
	/**
	 * synchronized ������ͨ�����ϣ����������ǵ�ǰ���ʵ��
	 * @return
	 */
	public synchronized int getNext(){
		return i++;
	}
	/**
	 * ���ξ�̬�������������ǵ�ǰ��Class�ֽ������
	 * Sequence.class
	 * @return
	 */
	public static synchronized int getPrevious(){
		return i--;
	}
	
	public int xx(){
		synchronized (Sequence.class) {
			if(i>0){
				return i;
			}else{
				return -1;
			}
		}
	}
	
	public static void main(String[] args) {
		Sequence s=new Sequence();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
//						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
//						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
//						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
