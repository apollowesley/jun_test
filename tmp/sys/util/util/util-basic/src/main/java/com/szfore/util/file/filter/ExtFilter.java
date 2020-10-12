package com.szfore.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;
/**
 * 根据后缀名过滤文件的实现
 * @author leiguanglong
 *
 */
public class ExtFilter implements FilenameFilter{
	
	private String[] fileExts;
	
	public ExtFilter(String... ext)
	{
		fileExts = ext;
	}
	
	public boolean accept(File dir, String name) 
	{
		int index = name.lastIndexOf(".");
		if(index < 0)
		{
			return false;
		}
		String ext = name.substring(index);
		
		for (String inputExt : fileExts) 
		{
			if(ext.equals(inputExt))
			{
				return true;
			}
		}
		return false;
	}
}
