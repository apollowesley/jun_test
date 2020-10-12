package com.szfore.util.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encryption {
	
	public static String encodeStr(String uploadContent) {
		String key = "Szfore68638";
		String md5Key = MD5.encrptMD5(key, MD5.UTF8);
		uploadContent = getUtfCodeValue(uploadContent);
		char[] contents = uploadContent.toCharArray();
		char[] md5Chars = md5Key.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i=0,j=0; i<contents.length; i++,j++) {
			if(j>=md5Chars.length) {
				j = 0;
			}
			sb.append((char)(contents[i]^md5Chars[j]));
		}
		return sb.toString();
	}
	
	public static String encodeStrNoUtf8Value(String uploadContent) {
		String key = "Szfore68638";
		String md5Key = MD5.encrptMD5(key, MD5.UTF8);
		char[] contents = uploadContent.toCharArray();
		char[] md5Chars = md5Key.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i=0,j=0; i<contents.length; i++,j++) {
			if(j>=md5Chars.length) {
				j = 0;
			}
			sb.append((char)(contents[i]^md5Chars[j]));
		}
		return sb.toString();
	}
	
	
	public static String getUtfCodeValue(String str) {
		StringBuffer sb = new StringBuffer();
		char[] strChars = str.toCharArray();
		for(int i=0; i<strChars.length; i++) {
			String s = Integer.toHexString(strChars[i]);
			int len = s.length();
			switch (len) {
			case 0:
				s = "0000";
				break;
			case 1:
				s = "000"+s;
				break;
			case 2:
				s = "00"+s;
				break;
			case 3:
				s = "0"+s;
				break;
			default:
				break;
			}
			sb.append("\\u"+s);
		}
		return sb.toString();
	}
	
	public static String decodeStr(String encodeStr) {
		if(encodeStr == null || "".equals(encodeStr)){
			return "";
		}
		String key = "Szfore68638";
		String md5Key = MD5.encrptMD5(key, MD5.UTF8);
		char[] encodeChars = encodeStr.toCharArray();
		char[] md5Chars = md5Key.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i=0,j=0; i<encodeChars.length; i++,j++) {
			if(j>=md5Chars.length) {
				j = 0;
			}
			sb.append((char)(encodeChars[i]^md5Chars[j]));
		}
		return sb.toString();
	}
	
	public static void encodeImg(String srcImgPath,String destImgPath) {
		File srcfile = new File(srcImgPath);
		if(!srcfile.exists()) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		String md5Key = MD5.encrptMD5("Szfore68638", MD5.UTF8);
		byte[] md5Byte = md5Key.getBytes();
		try {
			fis = new FileInputStream(srcfile);
			fos = new FileOutputStream(destImgPath);
			
			int len = 0;
			byte[] buffer = new byte[fis.available()];
			while((len = fis.read(buffer)) != -1) {
				for(int i=0,j=0; i<len; i++,j++) {
					if(j>=32) {
						j=0;
					}
					buffer[i] = (byte) (buffer[i]^md5Byte[j]);
				}
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis != null) {
					fis.close();
				}
				if(fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
