package org.coody.czone.test;

import java.lang.reflect.Constructor;

import org.coody.framework.core.util.ParameterNameUtil;
import org.nico.noson.Noson;

import com.zaxxer.hikari.HikariDataSource;

import redis.clients.jedis.JedisPool;

public class AAA {
	
	public AAA(){}
	
	public AAA(String aaa){}

	public static void main(String[] args) {
		//new JedisPool(poolConfig, host, port, timeout, password)
		Object obj=ParameterNameUtil.getExecutableParameters(HikariDataSource.class);
		System.out.println(Noson.reversal(obj));
	}
}
