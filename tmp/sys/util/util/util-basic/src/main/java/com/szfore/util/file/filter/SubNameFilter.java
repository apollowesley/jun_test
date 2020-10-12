package com.szfore.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;
/**
 * 根据后缀名过滤文件的实现
 * @author leiguanglong
 *
 */
public class SubNameFilter implements FilenameFilter{
	
	private String[] subNames;
	
	public SubNameFilter(String... subNames)
	{
		this.subNames = subNames;
	}
	
	public boolean accept(File dir, String name) 
	{
		for (String subName : subNames) 
		{
			if(name.contains(subName))
			{
				return true;
			}
		}
		return false;
	}
}
