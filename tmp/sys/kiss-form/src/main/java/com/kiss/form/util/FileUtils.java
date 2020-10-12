/**
 * FileUtils.java
 * hrrm-form
 * com.hrrm.kiss.form.util
 */
package com.kiss.form.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.kiss.form.bean.HeritrixFileBean;

/**
 * @author leyvi
 * @since 2013-8-13下午5:23:05 爬虫文件类
 */
public class FileUtils {

	/**
	 * @author leyvi
	 * @since 2013-8-13下午5:58:31
	 * @param dirPath
	 * @return List<HeritrixFileBean> 根据根目录获取文件夹下所有文件名称
	 */
	public static List<HeritrixFileBean> getFileNames(String dirPath) {
		File file = new File(dirPath);
		File[] subFiles = getSubFiles(file);
		return convertFile(subFiles);
	}

	/**
	 * @author leyvi
	 * @since 2013-8-13下午5:58:45
	 * @param file
	 * @return File[] 获取目录下的文件
	 */
	public static File[] getSubFiles(File file) {
		File[] files = new File[] {};
		if (file.isDirectory()) {
			files = file.listFiles();
		}
		return files;
	}

	/**
	 * @author leyvi
	 * @since 2013-8-13下午5:58:02
	 * @param files
	 * @return List<HeritrixFileBean> 将文件数组转换成List
	 */
	public static List<HeritrixFileBean> convertFile(File[] files) {
		List<HeritrixFileBean> beanFiles = new ArrayList<HeritrixFileBean>();
		if (files == null)
			return beanFiles;
		for (File file : files) {
			HeritrixFileBean bean = new HeritrixFileBean();
			bean.setFileName(file.getName());
			bean.setFileSize(file.length());
			bean.setFileUrl(encodeBase64(file.getAbsolutePath()));
			bean.setIsDirectory(file.isDirectory());
			beanFiles.add(bean);
		}
		return beanFiles;
	}
	
	public static String encodeBase64(String str){
		String result = "";
		if(StringUtils.isBlank(str)) return result;
		try {
			result =  Base64.encodeBase64URLSafeString(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String decodeBase64(String str){
		String result = "";
		if(StringUtils.isBlank(str)) return result;
		try {
			result = new String(Base64.decodeBase64(str),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getParentFilePath(String filePath){
		File file = new File(filePath);
		return encodeBase64(file.getParent());
	}
}
