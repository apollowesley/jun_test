package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class ReplaceValueByProperty implements Command {

	private String replacePro;
	private Object replaceVal;
	private String jugePro;
	private Object jugeVal;
	
	public ReplaceValueByProperty(String replacePro, Object replaceVal, String jugePro, Object jugeVal) 
	{
		this.replacePro = replacePro;
		this.replaceVal = replaceVal;
		this.jugePro = jugePro;
		this.jugeVal = jugeVal;
	}


	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.replaceValueByProperty(map, replacePro, replaceVal, jugePro, jugeVal);
	}

}
