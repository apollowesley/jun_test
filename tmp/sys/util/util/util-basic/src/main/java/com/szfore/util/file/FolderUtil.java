package com.szfore.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import com.szfore.util.file.filter.DefaultFilter;
import com.szfore.util.file.filter.ExtFilter;
import com.szfore.util.file.filter.FolderFilter;
import com.szfore.util.file.filter.SubNameFilter;
/**
 * 定义文件夹的操作
 * @author leiguanglong
 *
 */
public class FolderUtil {

	/**
	 * 返回文件夹下的文件夹列表
	 * @param fileDir
	 * @return
	 */
	public static List<File> listFolders(String fileDir)
	{
		FilenameFilter filter = new FolderFilter();
		return new Folder(fileDir, filter).scanFiles(fileDir);
	}
	
	/**
	 * 返回文件夹（包括子文件夹）下所有的文件
	 * @param fileDir
	 * @return
	 */
	public static List<File> listAllFiles(String fileDir)
	{
		FilenameFilter filter = new DefaultFilter();
		return new Folder(fileDir, filter).scanAllFileInDir(fileDir);
	}
	
	/**
	 * 返回文件夹下的文件列表
	 * @param fileDir
	 * @return
	 */
	public static List<File> listFiles(String fileDir)
	{
		FilenameFilter filter = new DefaultFilter();
		return new Folder(fileDir, filter).scanFiles(fileDir);
	}
	
	
	
	/**
	 * 返回文件夹（包括子文件夹）下所有的 后缀名为 exts 的文件
	 * @param fileDir
	 * @param exts 比如： .jpg, .png
	 * @return
	 */
	public static List<File> listAllExtFiles(String fileDir, String...exts)
	{
		FilenameFilter filter = new ExtFilter(exts);
		return new Folder(fileDir, filter).scanAllFileInDir(fileDir);
	}
	
	/**
	 * 返回文件夹下，后缀名为 exts 的文件列表
	 * @param fileDir
	 * @param exts  比如： .png , .jpg
	 * @return
	 */
	public static List<File> listExtFiles(String fileDir, String...exts)
	{
		FilenameFilter filter = new ExtFilter(exts);
		return new Folder(fileDir, filter).scanFiles(fileDir);
	}
	
	/**
	 * 返回文件夹（包括子文件夹）下所有的包含 subNames 的文件
	 * @param fileDir
	 * @param subNames 比如：_thumb, _group
	 * @return
	 */
	public static List<File> listAllSubNameFiles(String fileDir, String... subNames)
	{
		FilenameFilter filter = new SubNameFilter(subNames);
		return new Folder(fileDir, filter).scanAllFileInDir(fileDir);
	}
	
	/**
	 * 返回文件夹下，包含 subNames 的文件
	 * @param fileDir
	 * @param subNames  比如：_thumb, _group
	 * @return
	 */
	public static List<File> listSubNameFiles(String fileDir, String... subNames)
	{
		FilenameFilter filter = new SubNameFilter(subNames);
		return new Folder(fileDir, filter).scanFiles(fileDir);
	}
}
