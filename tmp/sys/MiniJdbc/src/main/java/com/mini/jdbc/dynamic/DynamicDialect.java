package com.mini.jdbc.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.mini.jdbc.dialect.Dialect;
/**
 * 动态数据源的方言
 * @author sxjun
 * @create 2017-4-26
 */
public class DynamicDialect {
	private Map<Object, Dialect> targetDialect;
	private Dialect defaultDialect;
	public void addDialect(Object key,Dialect value) {
		if(targetDialect==null)
			targetDialect = new HashMap<Object, Dialect>();
		targetDialect.put(key, value);
	}
	
	public void setTargetDialect(Map<Object, Dialect> targetDialect) {
		this.targetDialect = targetDialect;
	}
	
	public Dialect getDialect() {
		return determineTargetDialect();
	}

	public Dialect determineTargetDialect() {
		Assert.notNull(this.targetDialect, "Dialect router not initialized");
		Object lookupKey = determineCurrentLookupKey();
		Dialect dialect = this.targetDialect.get(lookupKey);
		if (dialect == null ) {
			dialect = this.defaultDialect;
		}
		if (dialect == null) {
			throw new IllegalStateException("Cannot determine target Dialect for lookup key [" + lookupKey + "]");
		}
		return dialect;
	}
	
	public Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType(); 
	}

	public void setDefaultDialect(Dialect defaultDialect) {
		this.defaultDialect = defaultDialect;
	}
}
