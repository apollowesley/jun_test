package org.nature4j.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 移动文件
	 * 
	 * @param oldFile
	 *            原文件
	 * @param newFile
	 *            新文件
	 */
	public static void move(File oldFile, File newFile) {
		FileInputStream oldFileInputStream = null;
		FileOutputStream newFileOutputStream = null;
		try {
			oldFileInputStream = new FileInputStream(oldFile);
			newFileOutputStream = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = oldFileInputStream.read(buf)) != -1) {
				newFileOutputStream.write(buf, 0, len);
			}
			newFileOutputStream.flush();
			oldFile.delete();
		} catch (IOException e) {
			LOGGER.error("move file error");
			throw new RuntimeException(e);
		} finally {
			try {
				if (oldFileInputStream != null) {
					oldFileInputStream.close();
				}
				if (newFileOutputStream != null) {
					newFileOutputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error("move file error");
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 *            原文件路径
	 * @param newPath
	 *            新文件路径
	 */
	public static void move(String oldPath, String newPath) {
		FileInputStream oldFileInputStream = null;
		FileOutputStream newFileOutputStream = null;
		try {
			File oldFile = new File(oldPath);
			if (!oldFile.exists()) {
				LOGGER.error("oldPath is not exists");
				throw new RuntimeException("oldPath is not exists");
			}
			File newFile = new File(newPath);
			newFile.getParentFile().mkdirs();
			oldFileInputStream = new FileInputStream(oldFile);
			newFileOutputStream = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = oldFileInputStream.read(buf)) != -1) {
				newFileOutputStream.write(buf, 0, len);
			}
			newFileOutputStream.flush();
			oldFile.delete();
		} catch (IOException e) {
			LOGGER.error("move file error");
			throw new RuntimeException(e);
		} finally {
			try {
				if (oldFileInputStream != null) {
					oldFileInputStream.close();
				}
				if (newFileOutputStream != null) {
					newFileOutputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error("move file error");
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 移动文件
	 * 
	 * @param oldFile
	 *            原文件
	 * @param newPath
	 *            新文件路径
	 */
	public static void move(File oldFile, String newPath) {
		FileInputStream oldFileInputStream = null;
		FileOutputStream newFileOutputStream = null;
		try {
			File newFile = new File(newPath);
			newFile.getParentFile().mkdirs();
			oldFileInputStream = new FileInputStream(oldFile);
			newFileOutputStream = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = oldFileInputStream.read(buf)) != -1) {
				newFileOutputStream.write(buf, 0, len);
			}
			newFileOutputStream.flush();
			oldFile.delete();
		} catch (IOException e) {
			LOGGER.error("move file error");
			throw new RuntimeException(e);
		} finally {
			try {
				if (oldFileInputStream != null) {
					oldFileInputStream.close();
				}
				if (newFileOutputStream != null) {
					newFileOutputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error("move file error");
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 字节写入到文件
	 * 
	 * @param bytes
	 *            要写入的字节
	 * @param file
	 *            生成的文件路径
	 */
	public static void byte2file(byte[] bytes, String file) {
		FileOutputStream fileOutputStream = null;
		try {
			File f = new File(file);
			f.getParentFile().mkdirs();
			fileOutputStream = new FileOutputStream(f);
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
		} catch (Exception e) {
			LOGGER.error("write bytes to file error");
			throw new RuntimeException(e);
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				LOGGER.error("FileOutputStream close error");
				throw new RuntimeException(e);
			}
		}

	}
	
	/**
	 * 字符串 写入到文件
	 * 
	 * @param str
	 *            要写入的字符串 
	 * @param file
	 *            生成的文件路径
	 */
	public static void string2file(String str, String file) {
		byte2file(str.getBytes(), file);
	}
	
	/**
	 * 获取ClassPath路径
	 * 
	 * @return ClassPath路径 eg:c:/user/nature-framework/target/classes/
	 */
	public static String getClassPath() {
		return FileUtil.class.getResource("/").getPath().substring(1);
	}
	
	public static String reduceLenStr(double len){
		String unit = "";
		if (len>=1024) {
			len = len/1024.0;
			unit = " KB";
			if (len>=1024) {
				len = len/1024.0;
				unit = " MB";
				if (len>=1024) {
					len = len/1024.0;
					unit = len+" GB";
					if (len>=1024) {
						len = len/1024.0;
						unit = len+" TB";
					}
				}
			}
		}else {
			unit = " B";
		}
		
		return Math.round(len)+unit;
	}
	
	public static double reduceLen(double len){
		if (len>=1024) {
			len = len/1024.0;
			if (len>=1024) {
				len = len/1024.0;
				if (len>=1024) {
					len = len/1024.0;
					if (len>=1024) {
						len = len/1024.0;
					}
				}
			}
		}
		
		return Math.round(len);
	}

}
