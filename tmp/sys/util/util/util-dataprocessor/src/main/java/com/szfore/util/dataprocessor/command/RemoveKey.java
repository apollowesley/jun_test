package com.szfore.util.dataprocessor.command;

import java.util.Map;

public class RemoveKey implements Command {

	private String[] keys;
	
	public RemoveKey(String... keys) {
		this.keys = keys;
	}

	public Map<String, Object> execute(Map<String, Object> map) {
		for (String key : keys) 
		{
			map.remove(key);
		}
		return map;
	}

}
