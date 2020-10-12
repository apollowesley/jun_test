package org.duomn.naive.web.upload.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UploadUtil {
	/** 上传文件时，文件的pcid */
	public static final String UPLOAD_FILE_PCID = "UPLOAD_FILE_PCID";
	/** 断点续传时，流继续上传的位置 */
	public static final String BROKEN_RESUME_POSITION = "BROKEN_RESUME_POSITION";
	/** 读取文件时的缓冲区字节数 */
	public static final int BUFLEN = 8190;
	

	/** 把断点续传的流，使用RandomAccessFile写入到未完成的文件中 */
	public static void breakStreamToFile(InputStream in, File file, long skipped) throws IOException {
		if (skipped == -1) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				copy(in, fos);
			} finally {
				if (fos != null) {
					fos.close();
				}
				if (in != null) {
					in.close();
				}
			}
		} else {
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(file, "rw");
				raf.seek(skipped);
				int readLen = 0;
		        byte[] buffer = new byte[BUFLEN];
		        while ((readLen = in.read(buffer, 0, BUFLEN)) != -1){
		        	raf.write(buffer, 0, readLen);
		        }
			} finally {
				if (raf != null) {
					raf.close();
				} 
				if (in != null) {
					in.close();
				}
			}
		}
	}
	
	/** 校验上传文件的MD5值 */
	public static String calculateMD5Sum(File file) throws IOException {
		StringBuffer ret = new StringBuffer();
		MessageDigest digest = null;
		
		InputStream md5InputStream = null;
		try {
			digest = MessageDigest.getInstance("MD5");
			
			md5InputStream = new FileInputStream(file);
			byte md5Buffer[] = new byte[BUFLEN];
			int readLen = 0;
			while ((readLen = md5InputStream.read(md5Buffer, 0, BUFLEN)) > 0) {
				digest.update(md5Buffer, 0, readLen);
			}
		} catch (NoSuchAlgorithmException e) {
			throw new IOException("No such algorithm [MD5].");
		} finally {
			if (md5InputStream != null) {
				md5InputStream.close();
			}
		}

		// Now properly format the md5 sum.
		byte md5sum[] = new byte[32];
		if (digest != null)
			md5sum = digest.digest();
		for (int i = 0; i < md5sum.length; i++) {
			ret.append(Integer.toHexString((md5sum[i] >> 4) & 0x0f));
			ret.append(Integer.toHexString(md5sum[i] & 0x0f));
		}

		return ret.toString();
	}

	public static boolean isStringType(String contentType) {
		if (contentType == null || contentType.trim().isEmpty()) return false;
		return contentType.indexOf("text/plain") != -1;
	}
	
	public static String getEncoding(String contentType, String defualtEncoding) {
		if (contentType == null || contentType.trim().isEmpty()) return defualtEncoding;
		int start = contentType.toLowerCase().indexOf("charset=");
		return start == -1 ? defualtEncoding : contentType.substring(start + 8).trim();
	}
	
	public static void copy(InputStream input, OutputStream output) throws IOException {
		int readLen = 0;
		byte[] buffer = new byte[BUFLEN];
        while ((readLen = input.read(buffer, 0, BUFLEN)) != -1){
        	output.write(buffer, 0, readLen);
        }
    }
}
