/**
 * 
 */
package com.autoscript.ui.core.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * xml 文件名 过滤器
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class XmlFilenameFilter implements FilenameFilter {

	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		if(name!=null){
			String tmp;
			tmp = name.toLowerCase();
			return tmp.endsWith(".xml");
		}
		return false;
	}

}
