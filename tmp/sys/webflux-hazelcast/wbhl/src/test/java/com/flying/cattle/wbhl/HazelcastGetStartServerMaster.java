package com.flying.cattle.wbhl;

import java.util.Queue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

public class HazelcastGetStartServerMaster {
    public static void main(String[] args) {
        // 创建一个 hazelcastInstance实例
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        // 创建集群Map
        IList<Object> clusterMap = instance.getList("myList");
        clusterMap.add("list0");
        clusterMap.add("list1");

        // 创建集群Queue
        Queue<String> clusterQueue = instance.getQueue("MyQueue");
        clusterQueue.offer("Hello hazelcast!");
        clusterQueue.offer("Hello hazelcast queue!");
    }
}