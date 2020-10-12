package com.sise.school.teach.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * 解密工具
 *
 * @author idea
 * @data 2018/9/22
 */
public class DecodeUtil {

    /**
     * 解密工具
     *
     * @param content
     * @return
     */
    public static String decodeStr(String content) {
        Base64 base64 = new Base64();
        return new String(base64.decode(content.getBytes()));
    }

    /**
     * 加盐的解密
     *
     * @param password
     * @return
     */
    public static String decodeStrWithSalt(String password, String salt) {
        Base64 base64 = new Base64();
        return new String(base64.decode(password.getBytes())).replace(salt,"");
    }
}
