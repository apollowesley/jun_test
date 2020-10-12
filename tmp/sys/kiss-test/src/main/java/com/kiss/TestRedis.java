package com.kiss;

import com.kiss.user.entity.User;

import redis.clients.jedis.Jedis;

public class TestRedis {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.44.128",6379);
		int i=0;
		//jedis.connect();
		/*for(;;){
			//jedis.set("测试"+i,"测试");
			System.out.println(jedis.get("测试"+i));
			i++;
			if(i==10000)break;
		}*/
		//jedis.disconnect();
		System.out.println(jedis.dbSize());
		User user = new User();
		user.setUsername("123");
		
	}
}
