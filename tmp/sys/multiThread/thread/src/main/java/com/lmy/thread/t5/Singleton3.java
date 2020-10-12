package com.lmy.thread.t5;

public class Singleton3 {
	private static Singleton3 instance=null;
	private Singleton3(){}
	public static Singleton3 getInstance(){
		if(instance==null){
			synchronized (Singleton3.class) {
				if(instance==null){
					instance=new Singleton3();//÷∏¡Ó÷ÿ≈≈–Ú
				}
			}
		}
		return instance;
	}
}
