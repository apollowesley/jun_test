package com.lmy.thread.t7;

public class Demo2 {
	public volatile boolean run=true;
	public static void main(String[] args) {
		Demo2 demo=new Demo2();
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=1;i<=10;i++){
					System.out.println("执行了第"+i+"次");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				demo.run=false;
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(demo.run){
					
				}
				System.out.println("执行完毕");
			}
		}).start();
	}
}
