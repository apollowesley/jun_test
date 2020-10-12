package com.lmy.thread.tb7;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
	public void a(Exchanger<String> exch){
		System.out.println("a������ʼִ��");
		try {
			System.out.println("a������ʼץȡ����...");
			Thread.sleep(3000);
			System.out.println("a����ץȡ���ݽ���...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("a�����ȴ��ԱȽ��");
		String res="12345";
		try {
			exch.exchange(res);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void b(Exchanger<String> exch){
		System.out.println("b������ʼִ��");
		try {
			System.out.println("b������ʼץȡ����...");
			Thread.sleep(3000);
			System.out.println("b����ץȡ���ݽ���...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res="12345";
		try {
			String value=exch.exchange(res);
			System.out.println("b������ʼ���бȶ�");
			System.out.println("�ȶԽ���� "+res.equals(value));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Exchanger<String> exch=new Exchanger<String>();
		ExchangerDemo d=new ExchangerDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.a(exch);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.b(exch);
			}
		}).start();
	}
}
