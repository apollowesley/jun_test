package com.mini.example;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.pool.DruidDataSource;
import com.mini.jdbc.cfg.MiniConfig;
import com.mini.jdbc.dynamic.DynamicDataSource;

public class CodeExample {
	
	public static void main(String[] args) {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://127.0.0.1/test?characterEncoding=utf8");
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("11111");
		druidDataSource.setInitialSize(3);
		druidDataSource.setMinIdle(3);
		druidDataSource.setMaxActive(20);
		druidDataSource.setMaxWait(60000);
		druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
		druidDataSource.setMinEvictableIdleTimeMillis(300000);
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		
		Map<Object,Object> datasources= new HashMap<Object,Object>();
		datasources.put("key", druidDataSource);
		
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(druidDataSource);
		dynamicDataSource.setTargetDataSources(datasources);
		dynamicDataSource.afterPropertiesSet();
		
		MiniConfig miniConfig = new MiniConfig();
		miniConfig.setDynamicDataSource(dynamicDataSource);
		
		/*MiniDao miniDao = new MiniDao();
		miniDao.setMiniConfig(miniConfig);
		
		Record record = new Record("users", "id");
		record.set("id", "1");
		miniDao.insert(record);
		System.out.println();*/
	}
}
