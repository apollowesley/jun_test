package net.domor.cg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtils {

	/**
	 * 行分隔符
	 */
	public static final String SYS_LINE_SEPARATOR = System.getProperty("line.separator");
	
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return (file.exists()) && (file.isFile());
	}

	public static boolean create(String filePath, String text, String encoding) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(filePath, encoding);
			printWriter.print(text);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
		return true;
	}

	public static boolean create(String filePath, String text) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileOutputStream(filePath));
			printWriter.print(text);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
		return true;
	}
	
	public static String readClasspathFile(String filePath) {
		return readClasspathFile(filePath, SYS_LINE_SEPARATOR);
	}
	
	public static String readClasspathFile(String filePath, String lineSeperator) {
		StringBuilder stringBuilder = new StringBuilder();
		InputStream is = null;
		BufferedReader bufferedReader = null;
		try {
			is = FileUtils.class.getResourceAsStream(filePath);
			bufferedReader = new BufferedReader(new InputStreamReader(is));
			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuilder.append(text);
				if ((lineSeperator == null) || ("".equals(lineSeperator)))
					continue;
				stringBuilder.append(lineSeperator);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe1) {
				ioe1.printStackTrace();
			}
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}
}
