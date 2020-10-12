package com.flying.cattle.wbhl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flying.cattle.wbhl.interceptor.IMapInterceptor;
import com.flying.cattle.wbhl.listener.IMapListener;
import com.flying.cattle.wbhl.listener.TopicListener;
import com.flying.cattle.wbhl.utils.Const;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ITopic;

@Configuration
public class HazelcastConfig {
	@Bean
	public Config config() {
		Config config = new Config();
		GroupConfig gc=new GroupConfig(Const.HAZELCAST_NAME);//解决同网段下，不同库项目
		
		config.setInstanceName("hazelcast-instance")
				.addMapConfig(new MapConfig().setName("configuration")
				.setMaxSizeConfig(new MaxSizeConfig(2000, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
				.setEvictionPolicy(EvictionPolicy.LRU).setTimeToLiveSeconds(-1))
				.setGroupConfig(gc);
		return config;
	}
	
	@Bean
	public HazelcastInstance hazelcastInstance(Config config) {
		HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config); 
		//分布式map监听
		IMap<Object, Object> imap = hzInstance.getMap(Const.MAP_NAME);
		imap.addLocalEntryListener(new IMapListener());
		//拦截器（没写内容）
		imap.addInterceptor(new IMapInterceptor());
		//发布/订阅模式
		ITopic<String> topic = hzInstance.getTopic(Const.TOPIC_NAME); 
		topic.addMessageListener(new TopicListener());
		
		return hzInstance;
	}
}
