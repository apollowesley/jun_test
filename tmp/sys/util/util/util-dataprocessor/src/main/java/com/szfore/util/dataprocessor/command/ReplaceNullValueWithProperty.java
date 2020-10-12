package com.szfore.util.dataprocessor.command;

import java.util.Map;

public class ReplaceNullValueWithProperty implements Command {

	private String nullValPro;
	private String newValPro;
	
	public ReplaceNullValueWithProperty(String nullValPro, String newValPro) 
	{
		this.newValPro = newValPro;
		this.nullValPro = nullValPro;
	}

	public Map<String, Object> execute(Map<String, Object> map) 
	{
		Object oldVal = map.get(nullValPro);
		if(oldVal == null)
		{
			Object newVal = map.get(newValPro);
			map.put(nullValPro, newVal);
		}
		return map;
	}

}
