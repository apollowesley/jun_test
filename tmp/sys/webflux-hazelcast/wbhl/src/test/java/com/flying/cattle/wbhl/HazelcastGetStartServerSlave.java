package com.flying.cattle.wbhl;

import java.util.Queue;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

public class HazelcastGetStartServerSlave {
    public static void main(String[] args) {
        //创建一个 hazelcastInstance实例
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IList<Object> clusterList = instance.getList("myList");
        Queue<String> clusterQueue = instance.getQueue("MyQueue");
        
        System.out.println("Map Value:" + clusterList.get(1));
        System.out.println("Queue Size :" + clusterQueue.size());
        System.out.println("Queue Value 1:" + clusterQueue.poll());
        System.out.println("Queue Value 2:" + clusterQueue.poll());
        System.out.println("Queue Size :" + clusterQueue.size());
    }
}
