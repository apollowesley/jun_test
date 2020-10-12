package com.site.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class EncrypBASE64 {
	/**
	 * BASE64解密
	 * 
	 * @param decrypt
	 * @return
	 * @throws Exception
	 */
    public static String decryptBASE64(String decrypt) throws Exception {
		return new String((new BASE64Decoder()).decodeBuffer(decrypt));
	}

	/**
	 * BASE64加密
	 * 
	 * @param enctypt
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(String enctypt) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(enctypt.getBytes());
	}

	public static void main(String[] args) throws Exception {
		String testString = "123abc";
		String data = encryptBASE64(testString);
		System.out.println("加密后：" + data);
		String byteArray = decryptBASE64(data);
		System.out.println("解密后：" + byteArray);

	}
}
