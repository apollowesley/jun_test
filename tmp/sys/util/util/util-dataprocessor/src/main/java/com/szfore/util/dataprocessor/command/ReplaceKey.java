package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class ReplaceKey implements Command {

	private String oldKey;
	private String newKey;

	public ReplaceKey(String oldKey, String newKey) 
	{
		this.oldKey = oldKey;
		this.newKey = newKey;
	}


	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.replaceKey(map, oldKey, newKey);
	}

}
