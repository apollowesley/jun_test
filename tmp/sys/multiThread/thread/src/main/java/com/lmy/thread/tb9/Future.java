package com.lmy.thread.tb9;

public class Future {
	private Product product;
	private boolean down;
	
	public synchronized void setProduct(Product product){
		if(down){
			return;
		}
		this.product=product;
		this.down=true;
		notifyAll();
	}
	public synchronized Product get(){
		while(!down){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return product;
	}
}
