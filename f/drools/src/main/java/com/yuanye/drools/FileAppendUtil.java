/**
 * @author cj 2016年9月21日下午5:21:28
 */
package com.yuanye.drools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class FileAppendUtil {
	/**
	 * 追加文件：使用RandomAccessFile方法
	 * @param fileName 文件名
	 * @param content 追加内容
	 */
	public static void appendToFileWithRandomAccessFile(String fileName, String content) throws IOException {
		RandomAccessFile randomFile=null;
		try {
			// 打开一个随机访问文件流，按读写方式
			randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
		}finally {
			randomFile.close();
		}
	}
	/**
	 * 追加文件：使用FileWriter方法
	 * @param fileName 文件名
	 * @param content 追加内容
	 */
	public static void appendToFileWithFileWriter(String fileName, String content) throws IOException{
		FileWriter writer=null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(fileName, true);
			writer.write(content);
		}finally {
			writer.close();
		}
	}
	/**
	 * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
	 * @param fileName 文件名
	 * @param content 追加内容
	 */
	public static void appendToFileWithFileOutputStream(String file, String conent) throws IOException{
		BufferedWriter out = null;
		try {
			FileOutputStream fos=new FileOutputStream(file, true);
			OutputStreamWriter osw=new OutputStreamWriter(fos);
			out=new BufferedWriter(osw);
			out.write(conent);
		} finally {
			out.close();
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "D:/TEMP/test1.txt";
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String content = i + " new append!\n";
			FileAppendUtil.appendToFileWithRandomAccessFile(fileName, content);
		}
		long end = System.currentTimeMillis();
		long time = end - begin;
		System.out.println(time);
		FileAppendUtil.appendToFileWithRandomAccessFile(fileName, "useTime:" + time);
	}
}