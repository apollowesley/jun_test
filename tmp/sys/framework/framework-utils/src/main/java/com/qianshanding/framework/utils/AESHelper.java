package com.qianshanding.framework.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Fish
 */
public class AESHelper {
    private static final Log logger = LogFactory.getLog(AESHelper.class);

    Cipher ecipher;
    Cipher dcipher;

    /**
     * Title: <br />
     * Description:
     */
    public AESHelper() {
        try {
            SecretKeySpec skey = new SecretKeySpec("9f265d42ab3c66d8f50a3a2e793a30c2".getBytes(), "AES");
            this.setupCrypto(skey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AESHelper(String key) {
        SecretKeySpec skey = new SecretKeySpec(getMD5(key), "AES");
        this.setupCrypto(skey);
    }

    private void setupCrypto(SecretKey key) {
        // Create an 8-byte initialization vector
        byte[] iv = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        try {
            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encrypt(InputStream in, OutputStream out) {
        byte[] buf = new byte[1024];
        try {
            out = new CipherOutputStream(out, ecipher);

            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param plaintext
     * @return
     * @Title: encrypt
     * @Description:
     * @author Fish
     * @date 2016年3月5日
     */
    public String encrypt(String plaintext) {
        try {
            byte[] ciphertext = ecipher.doFinal(plaintext.getBytes("UTF-8"));
            return byteToHex(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param in
     * @param out
     * @Title: decrypt
     * @Description:
     * @author Fish
     * @date 2016年3月5日
     */
    public void decrypt(InputStream in, OutputStream out) {
        try {
            byte[] buf = new byte[1024];
            // Bytes read from in will be decrypted
            in = new CipherInputStream(in, dcipher);

            // Read in the decrypted bytes and write the cleartext to out
            int numRead = 0;
            while ((numRead = in.read(buf)) >= 0) {
                out.write(buf, 0, numRead);
            }
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param hexCipherText
     * @return
     * @Title: decrypt
     * @Description:
     * @author Fish
     * @date 2016年3月5日
     */
    public String decrypt(String hexCipherText) {
        try {
            String plaintext = new String(dcipher.doFinal(hexToByte(hexCipherText)), "UTF-8");
            return plaintext;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(byte[] ciphertext) {
        try {
            String plaintext = new String(dcipher.doFinal(ciphertext), "UTF-8");
            return plaintext;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getMD5(String input) {
        try {
            byte[] bytesOfMessage = input.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(bytesOfMessage);
        } catch (Exception e) {
            return null;
        }
    }

    static final String HEXES = "0123456789ABCDEF";

    public static String byteToHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static byte[] hexToByte(String hexString) {
        int len = hexString.length();
        byte[] ba = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            ba[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return ba;
    }
}
