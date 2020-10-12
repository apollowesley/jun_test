package com.sise.school.teach.utils;


import org.apache.commons.codec.binary.Base64;

import java.util.Random;


/**
 * 加密工具
 *
 * @author idea
 * @data 2018/9/22
 */
public class EncodeUtil {

    /**
     * 盐的长度
     */
    private static int SALT_LENTH = 5;

    /**
     * 加密工具
     *
     * @param content
     * @return
     */
    public static String encodeStr(String content) {
        Base64 base64 = new Base64();
        return new String(base64.encode(content.getBytes()));
    }

    /**
     * 加了盐的加密函数
     *
     * @param password
     */
    public static String encodeStrWithSalt(String password, String salt) {
        Base64 base64 = new Base64();
        return new String(base64.encode((password + salt).getBytes()));
    }


    /**
     * 创建盐值
     *
     * @return
     */
    public static String creatSalt() {
        StringBuffer saltStb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < SALT_LENTH; i++) {
            saltStb.append(random.nextInt(20));
        }
        return saltStb.toString();
    }

    public static void main(String[] args) {
        System.out.println(creatSalt());
    }
}
