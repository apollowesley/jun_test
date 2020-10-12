package com.ly.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 文件（夹）获取处理类
 * 
 * @Version 1.6.1
 */
public class FileUtil {
	
	/**
	 * 获取文件
	 * 也可以获取符合类型的文件
	 * 
	 * @param filePath	路径
	 * @param postfixs	获取符合后缀(不带“.”)的文件
	 * @return
	 * @throws FileNotFoundException 	文件类型不符
	 */
	public static File getFile(String filePath,String...postfixs) throws FileNotFoundException{
		File file = new File(filePath);
		if(!file.exists()){
			throw new FileNotFoundException("未找到"+filePath+"文件！");
		}
		if(postfixs.length>0){
			String fix = getPostfix(file);
			for(int i=0;i<postfixs.length;i++){
				if(postfixs[i].equals(fix)){
					break;
				}else if(postfixs.length-1==i){
					throw new FileNotFoundException("未找到类型为"+postfixs[i]+"的文件！");
				}
			}
		}
		return file;
	}
	
	/**
	 * 获取目录
	 * 
	 * @param path	文件夹路径
	 * @return
	 * @throws IOException
	 */
	public static File getMkdirFile(String mkdirPath){
		File file = new File(mkdirPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
        }
		return file;
	}
	
	/**
	 * 创建文件，如果有就直接拿过来，没有就创建
	 * @param path
	 * @return
	 */
	public static File createFile(String path){
		File file = null;
		try {
			file = getFile(path);
		} catch (FileNotFoundException e) {
			file = new File(path);
			File parent = file.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
		}
		return file;
	}
	
	/**
	 * 获取文件后缀名（不带“.”）
	 * 
	 * @param file
	 * @return
	 */
	public static String getPostfix(File file){
		return getPostfix(file.getName());
	}
	/**
	 * 获取文件后缀名（不带“.”）
	 * 
	 * @param filename
	 * @return
	 */
	public static String getPostfix(String filename){
		return filename.substring(filename.lastIndexOf(".")+1);
	}
	
	/**
	 * 获取classes下指定文件夹下的文件流
	 * @param filename	文件名
	 * @param paths	文件路径
	 * @return
	 */
	public static InputStream getResourceAsStream(String filename,String...paths){
		InputStream inputStream = null;
		for (String path : paths) {
			inputStream = FileUtil.class.getClassLoader().getResourceAsStream(path+filename);
			if(inputStream!=null)
				break;
		}
		return inputStream;
	}
	
	/**
	 * 获取classes下properties文件流对象
	 * 
	 * @param properties	properties文件
	 * @return
	 * @throws IOException 
	 */
	public static Properties getPropertiesFile(String filename,String...paths){
		Properties Properties = new Properties();   
		try {
			Properties.load(getResourceAsStream(filename,paths));
		} catch (IOException e) {
			Properties = null;
		}   
		return Properties;
	}
	
	/**
	 * 获取src/main/resources下文件对象
	 * 或获取src/下文件对象
	 * @param filename
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static File getSrcFile(String filename,String... postfixs) {
		try {
			return getFile("src/main/resources/"+filename,postfixs);
		} catch (FileNotFoundException e) {
			try {
				return getFile("src/"+filename,postfixs);
			} catch (FileNotFoundException e1) {
				return null;
			}
		}
	}
	
	/**
	 * 获取URL
	 * @param name
	 * @return
	 */
	public static URL getURL(String name){
		return new FileUtil().getClass().getClassLoader().getResource(name);
	}
}
