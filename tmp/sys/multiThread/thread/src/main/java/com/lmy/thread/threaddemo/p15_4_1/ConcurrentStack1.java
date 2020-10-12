package com.lmy.thread.threaddemo.p15_4_1;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack1<E> {
	private static class Node<E>{
		public final E item;
		public Node<E> next;
		public Node(E item) {
			this.item = item;
		}
	}
	AtomicReference<Node<E>> top=new AtomicReference<ConcurrentStack1.Node<E>>();
	public void push(E item){
		Node<E> oldHead=top.get();
		Node<E> newHead = new Node<E>(item);
		do {
			newHead.next=oldHead;
		} while (!top.compareAndSet(oldHead, newHead));
	}
	public E pop(){
		Node<E> newHead;
		Node<E> oldHead;
		do {
			oldHead=top.get();
			if(oldHead==null){
				return null;
			}
			newHead=oldHead.next;
		} while (!top.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}
}
