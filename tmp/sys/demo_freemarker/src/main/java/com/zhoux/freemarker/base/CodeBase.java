package com.zhoux.freemarker.base;
/**
 * 
 * @author zhoux
 * @Date Apr 1, 2017
 * @Email zhoux@souche.com
 * @Desc
 */
public interface CodeBase {
	/**
	 * 
	 * @param tableName 根据那张表生成代码
	 * @param fileType 生成的文件类型：javaBean ,interface,service ,dao 暂时只支持生成javabean
	 * @return
	 */
	public boolean createCodeFile(String tableName,String fileType,String codePath);
	
}
