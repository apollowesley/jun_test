package com.szfore.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.szfore.util.basic.StringUtil;

public class FileUtil {
	
	/**
	 * 将文件列表移动到文件夹
	 * @param files
	 * @param destFolder
	 */
	public static void moveTo(List<File> files, String destFolder)
	{
		if(files == null || files.size() == 0)
		{
			return;
		}
		
		File dir = new File(destFolder);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		
		try {
			for (File file : files) 
			{
				if(isTwoPathsEqual(file.getParent(), destFolder))
				{
					continue;
				}
				jodd.io.FileUtil.moveFileToDir(file.getAbsolutePath(), destFolder);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 用文件所在的文件夹的名称重命名文件
	 * @param files
	 * @return
	 */
	public static List<File> renameWithParentName(List<File> files)
	{
		if(files == null || files.size() == 0)
		{
			new ArrayList<File>();
		}
		
		List<File> newNameFiles = new ArrayList<File>();
		for (File file : files) 
		{
			String newName = file.getParent() + "/" + file.getParentFile().getName() + "." + StringUtil.subFileExt(file.getAbsolutePath());
			
			File newNameFile = new File(newName);
			file.renameTo(newNameFile);
			newNameFiles.add(newNameFile);
		}
		return newNameFiles;
	}
	
	/**
	 * 判断两个文件路径是否一样
	 * @param pathA
	 * @param pathB
	 * @return
	 */
	public static boolean isTwoPathsEqual(String pathA, String pathB)
	{
		File fileA = new File(pathA);
		File fileB = new File(pathB);
		
		return fileA.getAbsolutePath().equals(fileB.getAbsolutePath());
	}
	
	/**
	 * 读取文件内容到list
	 * @param filePath
	 * @return
	 */
	public static List<String> readFileToList(String filePath)
	{
		File file = new File(filePath);
		if(!file.exists())
		{
			System.out.println("文件路径错误");
			return null;
		}
		List<String> textLines = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			
			while((line = bufferedReader.readLine()) != null)
			{
				textLines.add(line);
			}
			bufferedReader.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textLines;
	}
	
	/**
	 * 读取文件内容到str
	 * @param filePath
	 * @return
	 */
	public static String readFileToStr(String filePath)
	{
		List<String> textLines = readFileToList(filePath);
		StringBuilder strBuilder = new StringBuilder();
		for (String string : textLines) 
		{
			strBuilder.append(string);
		}
		return strBuilder.toString();
	}
	
	/**
	 * 给文件名加上 suffix 后缀
	 * 例如: ttt.jpg 变为  ttt_link.jpg
	 * @param file
	 * @param suffix
	 */
	public static void addSuffixToFileName(File file, String suffix)
	{
		StringBuilder newFileName = new StringBuilder();
		newFileName.append(file.getParent())
				   .append("/")
				   .append(StringUtil.subFileName(file.getName()))
				   .append(suffix)
				   .append(".")
				   .append(StringUtil.subFileExt(file.getName()));
		File destFile = new File(newFileName.toString());
		file.renameTo(destFile);
	}
	
	/**
	 * 给文件名加上 suffix 后缀
	 * 例如: ttt.jpg 变为  ttt_link.jpg
	 * @param file
	 * @param suffix
	 */
	public static void addSuffixToFileNames(List<File> files, String suffix)
	{
		for (File file : files) 
		{
			addSuffixToFileName(file, suffix);
		}
	}
	
	/**
	 * 删除文件名的 suffix 后缀
	 * 例如： ttt_link.jpg 变为 ttt.jpg
	 * @param file
	 * @param suffix
	 */
	public static void deleteNameSuffixOf(File file, String suffix)
	{
		int suffixIndex = file.getName().lastIndexOf(suffix);
		String subFileName = file.getName().substring(0, suffixIndex);
		StringBuilder newFileName = new StringBuilder();
		newFileName.append(file.getParent())
				   .append("/")
				   .append(subFileName)
				   .append(".")
				   .append(StringUtil.subFileExt(file.getName()));
		File destFile = new File(newFileName.toString());
		file.renameTo(destFile);
	}
	
	/**
	 * 删除文件名的 suffix 后缀
	 * 例如： ttt_link.jpg 变为 ttt.jpg
	 * @param file
	 * @param suffix
	 */
	public static void deleteNameSuffixOf(List<File> files, String suffix)
	{
		for (File file : files) 
		{
			deleteNameSuffixOf(file, suffix);
		}
	}
	/*public static void main(String[] args) {
		List<File> files = FolderUtil.listSubNameFiles("F:/imageFlasher/Module Three/ttt", "_link");
		FileUtil.deleteNameSuffixOf(files, "_link");
	}*/
	
}
