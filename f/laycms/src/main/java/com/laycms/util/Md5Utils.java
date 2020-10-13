package com.laycms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;


public class Md5Utils {
	/** 生成32位码 */
	public final static String getLongToken(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/** 生成16位码 */
	public final static String getShortToken(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	public static int getRandomSalt(){
		return rand(100000, 999999);
	}
	
	//随机生成字母+数字
	public static String getRandomString(int n){
		return RandomStringUtils.random(n, "0123456789abcdefghjkmnpqrstuvwxyz");
	}
	
	//随机生成数字
	public static String getRandomNum(int n){
		return RandomStringUtils.random(n, "1234567890");
	}
	private static int rand(int min, int max){
		Random random = new Random();
		if(min < max){
			if(min > 0){
				return random.nextInt(max-min+1)+min;
			}else{
				return random.nextInt(max+1);
			}
		}else{
			return min;
		}
	}
		
}