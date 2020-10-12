package com.ly.utils.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * inputStream和outputStream互转
 * 
 * @version 1.0
 */
public class ConvertUtil {
	/**
	 * inputStream转outputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream parse(InputStream in) throws Exception {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			swapStream.write(ch);
		}
		return swapStream;
	}

	/**
	 * outputStream转inputStream
	 * 
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayInputStream parse(OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream;
	}

	/**
	 * inputStream转String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String parseString(InputStream in) throws Exception {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			swapStream.write(ch);
		}
		return swapStream.toString();
	}

	/**
	 * OutputStream 转String
	 * 
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public static String parseString(OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream.toString();
	}

	/**
	 * String转inputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayInputStream parseInputStream(String in) throws Exception {
		ByteArrayInputStream input = new ByteArrayInputStream(in.getBytes());
		return input;
	}

	/**
	 * String 转outputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream parseOutputStream(String in) throws Exception {
		return parse(parseInputStream(in));
	}
	
	/**
	 * 将输入流转文件最大409600字节400K
	 * @param ins
	 * @param file
	 * @throws IOException
	 */
	public static File parseFile(InputStream ins,File file) throws IOException{
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[409600];
		   while ((bytesRead = ins.read(buffer, 0, 409600)) != -1) {
		      os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		   return file;
		}
}
