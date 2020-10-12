package com.slavic.veles.utils;

import ch.qos.logback.core.encoder.ByteArrayUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltPwdUtils {
	private static String confuse(String str1, String str2) {
		String s1 = ByteArrayUtil.toHexString(str1.getBytes());
		String s2 = ByteArrayUtil.toHexString(str2.getBytes());
		return s1.substring(0, s1.length() / 2) + s2 + s1.substring(s1.length() / 2);
	}

	public static String salt(String password, String salt) {
		try {
			byte[] bytes = MessageDigest.getInstance("SHA-256")
					.digest(SaltPwdUtils.confuse(salt, password).getBytes());
			return ByteArrayUtil.toHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	public static String generateSalt() {
		byte[] bytes = new byte[32];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return ByteArrayUtil.toHexString(bytes);
	}
}
