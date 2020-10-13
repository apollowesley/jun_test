/**
 * 
 */
package com.autoscript.ui.core.processor.file;

/**
 * 脚本文件接口
 * 作者:龙色波
 * 日期:2013-10-14
 */
public interface IAutoScriptFile {
	/**
	 * 创建并写入文件
	 * @param fileName 文件名
	 * @param content 文件内容
	 * @param append  是不为最加方式
	 * @param charset 字符集s
	 * @throws Exception 
	 */
	public void createFile(String fileName,String content,String charset,boolean append) throws Exception;
}
