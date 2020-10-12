package com.szfore.util.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.szfore.util.file.filter.DefaultFilter;
/**
 * 封装文件夹的操作
 * @author leiguanglong
 *
 */
public class Folder {
	private List<Folder> subFolders;
	private List<File> files;
	private List<File> allFiles;
	
	private String folderPath;
	
	private FilenameFilter filenameFilter;
	
	public Folder(String folderPath, FilenameFilter filenameFilter)
	{
		this.folderPath = folderPath;
		subFolders = new ArrayList<Folder>();
		files = new ArrayList<File>();
		
		if(filenameFilter == null)
		{
			filenameFilter = new DefaultFilter();
		}
		this.filenameFilter = filenameFilter;
	}
	
	public Folder(String folderPath)
	{
		filenameFilter = new DefaultFilter();
	}
	
	/**
	 * 返回文件夹下，后缀名为 exts 的文件列表
	 * @param fileDir
	 * @param exts  比如： .png , .jpg
	 * @return
	 */
	public List<File> scanFiles(String fileDir)
	{
		File file = new File(fileDir);
		File[] fileArray = file.listFiles(filenameFilter);
		
		Collections.addAll(files, fileArray);
		return files;
	}
	
	/**
	 * 返回文件夹（包括子文件夹）下所有的 后缀名为 exts 的文件
	 * @param fileDir
	 * @param exts 比如： .jpg, .png
	 * @return
	 */
	public List<File> scanAllFileInDir(String fileDir)
	{
		allFiles = new ArrayList<File>();
		File file = new File(fileDir);
		if(!file.exists())
		{
			return allFiles;
		}
		
		files = scanFiles(fileDir);
		allFiles.addAll(files);
		
		subFolders = scanFolders(fileDir);
		for (Folder folder : subFolders) 
		{
			List<File> subFolderFiles = folder.scanAllFileInDir(folder.folderPath);
			allFiles.addAll(subFolderFiles);
		}
		
		return allFiles;
	}
	
	/**
	 * 返回文件夹下的文件夹
	 * @param fileDir
	 * @return
	 */
	private List<Folder> scanFolders(String fileDir)
	{
		File file = new File(fileDir);
		if(!file.exists())
		{
			return new ArrayList<Folder>();
		}
		File[] subFiles = file.listFiles();
		
		List<Folder> folders = new ArrayList<Folder>();
		for (File f : subFiles) 
		{
			if(f.isDirectory())
			{
				Folder folder = new Folder(f.getAbsolutePath(), filenameFilter);
				folders.add(folder);
			}
		}
		return folders;
	}
}
