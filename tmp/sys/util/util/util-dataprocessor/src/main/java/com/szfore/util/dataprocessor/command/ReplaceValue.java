package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class ReplaceValue <K, V> implements Command {

	private Map<K, V> newValueMap;
	private String[] selfKeys;
	
	public ReplaceValue(Map<K, V> newValueMap, String... selfKeys) 
	{
		this.newValueMap = newValueMap;
		this.selfKeys = selfKeys;
	}


	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.replaceValue(map, newValueMap, selfKeys);
	}

}
