package net.domor.cg.util;

import java.io.File;

public class FolderUtils {

	/**
	 * 文件分隔符
	 */
	public static final String SYS_FILE_SEPARATOR = File.separator;
	
	/**
	 * 行分隔符
	 */
	public static final String SYS_LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * 删除文件或目录
	 * @param file 文件或目录
	 * @return 成功删除文件或目录时，返回true；否则返回false
	 */
	public static boolean delete(File file) {
		if(file == null || !file.exists() || !file.isDirectory()) {
			return false;
		}
		File [] files = file.listFiles();
		for(File f : files) {
			if(f.isDirectory()) {
				if(!delete(f)) {
					return false;
				}
			} else {
				//删除单个文件时出现异常，返回false；如果成功删除单个文件，则继续执行循环
				if(!f.delete()) {
					return false;
				}
			}
		}
		return file.delete();
	}
	
	/**
	 * 判断目录是否存在
	 * @param folderPath 目录路径
	 * @return 是否是目录并且已经存在
	 */
	public static boolean exists(String folderPath) {
		File file = new File(folderPath);
		return (file.isDirectory()) && file.exists();
	}
	
	/**
	 * 创建路径名指定的目录
	 * @param folderPath 目录路径
	 * @return 目录已存在或者目录创建成功，返回true；否则返回false
	 */
	public static boolean mkdir(String folderPath) {
		File file = new File(folderPath);
		if(file.exists()) {
			if(file.isDirectory()) {
				return true;
			} else {
				return file.mkdir();
			}
		}
		return file.mkdir();
	}
	
	/**
	 * 创建路径名指定的目录，包括所有必需但不存在的父目录
	 * @param folderPath 目录路径
	 * @return 目录已存在或者目录创建成功，返回true；否则返回false
	 */
	public static boolean mkdirs(String folderPath) {
		File file = new File(folderPath);
		if(file.exists()) {
			if(file.isDirectory()) {
				return true;
			} else {
				return file.mkdirs();
			}
		}
		return file.mkdirs();
	}
	
}
