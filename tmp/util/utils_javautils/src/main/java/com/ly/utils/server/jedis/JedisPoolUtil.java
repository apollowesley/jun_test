package com.ly.utils.server.jedis;


import java.lang.reflect.Method;
import java.util.Properties;

import com.ly.utils.dynamic.Reflect;
import com.ly.utils.io.FileUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis数据库操作单机版链接工具
 * <br>
 * <em style="color:red;">
 * 在src下或src/main/resources下
 * 建立jedis.properties文件或建立jedis/jedis.properties文件
 * <br>
 * 文件内容:
 * <br> edis_url = 192.168.31.134
 * <br> edis_post = 6379
 * <br> ##################################################
 * <br> ###########JedisPoolConfig的set方法###############
 * <br> 最大连接数
 * <br> axTotal = 30
 * <br> 最大连接空闲数
 * <br> axIdle = 2
 * <br> 每次释放连接的最大数目
 * <br> umTestsPerEvictionRun = 1024
 * <br> 释放连接的扫描间隔（毫秒）
 * <br> imeBetweenEvictionRunsMillis = 30000
 * <br> 连接最小空闲时间
 * <br> inEvictableIdleTimeMillis = 1800000
 * <br> 连接空闲多久后释放, 当空闲时间>该值 且 空闲连接>最大空闲连接数 时直接释放
 * <br> oftMinEvictableIdleTimeMillis = 10000
 * <br> 获取连接时的最大等待毫秒数,小于零:阻塞不确定的时间,默认-1
 * <br> axWaitMillis = 1500
 * <br> 在获取连接的时候检查有效性, 默认false
 * <br> estOnBorrow = true
 * <br> 在空闲时检查有效性, 默认false
 * <br> estWhileIdle = true
 * <br> 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
 * <br> lockWhenExhausted = false
 * </em>
 * 
 * @version 1.5
 */
public class JedisPoolUtil {
	/**
	 * Jedis连接池
	 */
	private static JedisPool jedisPool;

	/**
	 * 初始化连接池
	 */
	static {
		//配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		Properties pro = FileUtil.getPropertiesFile("jedis.properties","","jedis/");
		String url = pro.get("jedis_url").toString();
		Integer post = Integer.parseInt(pro.get("jedis_post").toString());
		
		//设置配置对象值
		for(Object key : pro.keySet()){
			setConfig(config, key.toString(), pro.get(key).toString());
		}
		
		jedisPool = new JedisPool(config,url,post);
	}
	
	/**
	 * 设置值
	 * @param config		JedisPoolConfig对象
	 * @param key			JedisPoolConfig的set方法
	 * @param value			值
	 * @throws Exception
	 */
	private static void setConfig(JedisPoolConfig config, String key ,String value) {
		Method method = Reflect.getSetMethod(JedisPoolConfig.class, key);
		if(method != null){
			try {
				Reflect.invokeSetValue(method, config, value, null);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("jedis设置"+key.toString()+"失败！");
			}
		}
	}
	
	/**
	 * 获取连接池
	 * @return
	 */
	public static JedisPool getJedisPool() {
		return jedisPool;
	}
	
	/**
	 * 获取Jedis
	 * @return
	 */
	public static Jedis getJedis(){
		return jedisPool.getResource();
	}
	
}