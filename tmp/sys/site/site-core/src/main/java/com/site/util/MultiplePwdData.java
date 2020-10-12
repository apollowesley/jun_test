package com.site.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 支持以下方式混合加密 <br/>
 * 加密顺序： BASE64->DES->BASE64 <br/>
 * 加密顺序：BASE64->AES->BASE64
 * 
 * @param str
 * @param key
 * @return
 * @throws Exception
 */
@SuppressWarnings("restriction")
public class MultiplePwdData {

    // private static final String ALGORITHM = "AES";
    private static final String ALGORITHM = "DES";
    private static final BASE64Encoder BASE64_ENCODER = new BASE64Encoder();
    private static final BASE64Decoder BASE64_DECODER = new BASE64Decoder();

    /**
     * 加密顺序：BASE64->DES->BASE64
     * 
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String encode(String str, String key) {
        String code = BASE64_ENCODER.encode(str.getBytes());
        byte[] b = null;
        try {
            b = encrypt(code.getBytes(), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        code = BASE64_ENCODER.encode(b);
        return code;
    }

    /**
     * 解密顺序：BASE64->DES->BASE64
     * 
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String decode(String str, String key) {
        byte[] b1 = null;
        byte[] b2 = null;
        byte[] b3 = null;
        try {
            b1 = BASE64_DECODER.decodeBuffer(str);
            b2 = decrypt(b1, key);
            b3 = BASE64_DECODER.decodeBuffer(new String(b2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(b3);
    }

    /**
     * 加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = genKey(key.getBytes());
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data, 0, data.length);
    }

    /**
     * 解密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = genKey(key.getBytes());
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data, 0, data.length);
    }

    /**
     * 转换密钥
     * 
     * @param key
     * @return
     * @throws Exception
     */
    private static Key genKey(byte[] key) throws Exception {
        Key k = null;
        try {
            KeyGenerator keyG = KeyGenerator.getInstance(ALGORITHM);
            keyG.init(new SecureRandom(key));
            k = keyG.generateKey();
            key = null;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
        return k;
    }

    /**
     * 测试
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 加密密文随算法不同，加密密文不同，长度一样。
        String str = "12345abcde!@#$%^&*()";
        String key = "ff2f10b642ad44aca3c49f8a16c526f4ff2f";
        // String key = "ff2f10b642";
        // String key = " ";
        // String key = "dsfgdfggh";
        System.out.println("明文:" + str);
        String code1 = encode(str, key);
        System.out.println("加密:" + code1);
        String code2 = decode(code1, key);
        System.out.println("解密:" + code2);

    }
}
