package com.lijian.devutils.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionUtils {

	private static final String encryptPass = "lijian_encrptpwd_lizi~!@$%#";
	private static final int keyLength = 128;
	
	/**
	 * 解密
	 * @param content
	 * @param password 如果为null,使用默认的密码
	 * @return
	 */
	public static String decryptContent(String content, String password) {

		byte[] byteContent = BlobUtil.parseHexStr2Byte(content);
		try {
			SecretKeySpec key=null;
			if(password!=null){
				key = getKey(password);
			}else{
				key=getKey(encryptPass);
			}
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return new String(result, "utf-8"); // 加密
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 加密
	 * @param content
	 * @param password 如果为null,使用默认的密码
	 * @return
	 */
	public static String encryptContent(String content, String password) {

		try {
			SecretKeySpec key=null;
			if(password!=null){
				key = getKey(password);
			}else{
				key=getKey(encryptPass);
			}
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return BlobUtil.parseByte2HexStr(result); // 加密
		} catch (Exception e) {
		}

		return null;
	}

	public static SecretKeySpec getKey(String seed) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(seed.getBytes());
		kgen.init(keyLength, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

		return key;
	}

	
//	public CredDecryptResult getCredDecryptResultFromCredential(
//			String credential) throws CredentialExpiredException,
//			CredentialException {
//
//		CredDecryptResult res = decryptCredential(credential);
//
//		if (res == null) {
//			throw new CredentialException("session credential Illegal");
//		}
//
//		long passed = System.currentTimeMillis() - res.getCreateTime();
//
//		if (passed > this.sessionExpire) { 
//			throw new CredentialExpiredException(
//					"session scope credential expired");
//		}
//
//		return res;
//	}

//	private CredDecryptResult decryptCredential(String credential) {
//
//		String decryped = decryptContent(credential, encryptPass);
//
//		if (decryped == null) {
//			return null;
//		}
//		//[0]:存用户id. [1]存创建时间
//		String[] ss = decryped.split("\\|");
//
//		String userId = ss[0];
//		long createTime = Long.parseLong(ss[1]);
//		
//		return new CredDecryptResult(Long.parseLong(userId),createTime, 1);
//	}
//
//	
//	public String genCredential(long userId) {
//		long time = System.currentTimeMillis();
//
//		String cred =  userId + "|" + time;
//
//		return encryptContent(cred, encryptPass);
//	}
	
}
