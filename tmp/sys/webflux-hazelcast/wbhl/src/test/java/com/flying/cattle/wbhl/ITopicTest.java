package com.flying.cattle.wbhl;

import com.flying.cattle.wbhl.utils.Const;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

public class ITopicTest {
	public static void main(String[] args) {
		Config config = new Config();
		GroupConfig gc=new GroupConfig(Const.HAZELCAST_NAME);
		config.setGroupConfig(gc);
		HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config); 
		
		ITopic<String> topic = hzInstance.getTopic(Const.TOPIC_NAME); 
		topic.publish("我是一个topic消息！");
    }
}
