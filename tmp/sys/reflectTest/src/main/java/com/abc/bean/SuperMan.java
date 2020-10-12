package com.abc.bean;

public class SuperMan extends Preson implements ActionInterface {
	
	private boolean BuueBriefs;
	
	public void fly() {
		System.out.println("超人会飞耶~~");
	}
	

	@Override
	public void walk(int m) {
		
		System.out.println("超人会走耶~~"+m+"米就走不动了！");
		
	}


	public boolean isBuueBriefs() {
		return BuueBriefs;
	}


	public void setBuueBriefs(boolean buueBriefs) {
		BuueBriefs = buueBriefs;
	}
	
}
