package org.nature4j.framework.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

public class FileData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static String docx="application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	
	public InputStream inputStream;
	public String fileName;
	public String contentType;
	
	/**
	 *  返回文件到页面
	 * @param inputStream 返回流
	 * @param fileName 文件名
	 * @param contentType 文件类型
	 */
	public FileData(InputStream inputStream, String fileName, String contentType) {
		this.inputStream = inputStream;
		this.fileName = fileName;
		this.contentType = contentType;
	}
	
	/**
	 * 返回文件到页面
	 * @param inputStream 返回流
	 * @param fileName 返回文件名称
	 */
	public FileData(InputStream inputStream, String fileName) {
		this.inputStream = inputStream;
		this.fileName = fileName;
	}
	
	/**
	 * 返回文件到页面
	 * @param filePath 返回文件路径
	 * @param fileName 返回文件名称
	 */
	public FileData(String filePath, String fileName) {
		
		try {
			this.inputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.fileName = fileName;
	}
	
	/**
	 * 返回文件到页面
	 * @param file 返回文件
	 * @param fileName 返回文件名称
	 */
	public FileData(File file, String fileName) {
		
		try {
			this.inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.fileName = fileName;
	}
	
	/**
	 * 返回文件到页面
	 * @param file 返回文件
	 */
	public FileData(File file) {
		try {
			this.inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.fileName = file.getName();
	}
	
	/**
	 * 返回文件到页面
	 * @param file 返回文件路径
	 */
	public FileData(String filePath) {
		File file = new File(filePath);
		try {
			this.inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.fileName = file.getName();
	}
	
}
