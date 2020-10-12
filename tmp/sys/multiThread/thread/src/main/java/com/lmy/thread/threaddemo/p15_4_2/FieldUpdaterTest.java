package com.lmy.thread.threaddemo.p15_4_2;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class FieldUpdaterTest {
	public volatile String match;
	public static void main(String[] args) {
		FieldUpdaterTest t=new FieldUpdaterTest();
		t.match="str";
		AtomicReferenceFieldUpdater<FieldUpdaterTest, String> matchUpdater=
				AtomicReferenceFieldUpdater.newUpdater(FieldUpdaterTest.class, String.class, "match");
		new Thread(new Runnable() {
			@Override
			public void run() {
				matchUpdater.compareAndSet(t, "str", "hello");
				System.out.println(t.match);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				matchUpdater.compareAndSet(t, "a", "world");
				System.out.println(t.match);
			}
		}).start();
	}
}
