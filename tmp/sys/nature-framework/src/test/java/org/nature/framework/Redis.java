package org.nature.framework;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.nature.framework.serialize.User;
import org.nature.framework.util.SerializeUtil;

import redis.clients.jedis.Jedis;

public class Redis {
	public static void main(String[] args) throws URISyntaxException {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis(new URI("http://192.168.2.129:6379"));
		jedis.auth("root");
		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setAge(18);
		user.setName("张三");
		user.addr = "中国";
		list.add(user);
		user = new User();
		user.addr = "riben";
		list.add(user);
		SerializeUtil defaultSerialize = new SerializeUtil();
		byte[] serilize = defaultSerialize.serilize(list);
		jedis.set("userlist".getBytes(), serilize);
		byte[] bs = jedis.get("userlist".getBytes());
		Object deserilize = defaultSerialize.deserilize(bs);
		List<User> us =  (List<User>) deserilize;
		
		System.out.println("us:"+us.toString());
	}
}
