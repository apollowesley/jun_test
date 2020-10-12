package com.szfore.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;
/**
 * 
 * @author leiguanglong
 *
 */
public class DefaultFilter implements FilenameFilter{
	
	public boolean accept(File dir, String name) 
	{
		File file = new File(dir + "/" + name);
		return file.isFile();
	}
}
