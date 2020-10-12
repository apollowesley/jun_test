package com.lmy.thread.threaddemo.p15_3;

import java.util.concurrent.atomic.AtomicReference;

public class CasNumberRange {
	private static class IntPair{
		final int lower;
		final int upper;
		public IntPair(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}
	private final AtomicReference<IntPair> values=new AtomicReference<CasNumberRange.IntPair>(new IntPair(0,0));
	public int getLower(){
		return values.get().lower;
	}
	public int getUpper(){
		return values.get().upper;
	}
	public void setLower(int i){
		while(true){
			IntPair oldV=values.get();
			if(i>oldV.upper){
				throw new IllegalArgumentException("can't set lower to "+i+" >upper");
			}
			IntPair newV=new IntPair(i,oldV.upper);
			if(values.compareAndSet(oldV, newV)){
				return;
			}
		}
	}
	
}
