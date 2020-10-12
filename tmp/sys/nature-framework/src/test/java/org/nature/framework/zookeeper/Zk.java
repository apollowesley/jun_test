package org.nature.framework.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Zk{
	
	public static void main(String[] args) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		Watcher watcher = new Watcher(){
			public void process(WatchedEvent event) {
				System.out.println(event.getState());
				if (event.getState()==KeeperState.SyncConnected) {
					//latch.countDown();
				}
			}};
		ZooKeeper zooKeeper = new ZooKeeper("192.168.2.129", 1281, watcher, false);
		latch.await();
		
		byte[] data = zooKeeper.getData("/myconfig", watcher, null);
		System.out.println(new String(data));
		
	}

}
