/**  
 * Project Name:xjhws  
 * File Name:RandomCodeUtils.java  
 * Package Name:com.xjh.api.util  
 * Date:2015年4月21日下午8:14:24  
 * Copyright (c) 2015, xjh.com All Rights Reserved.  
 *  
 */
package com.site.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Description (验证码工具类) <br/>
 */
public class RandomCodeUtils {

    /**
     * 
     * getPhoneVCode:(获取手机验证码).
     * 
     * @return
     */
    public static String getPhoneVCode() {
        return getRandomSample(4);
    }

    /**
     * 
     * getRandomStr:(指定长度获取随机数字字母字符).
     * 
     * 
     * @param offset
     *            偏移量
     * @return
     */
    public static String getRandomStr(int offset) {
        return getRandomSample(offset);
    }

    /**
     * 
     * getRandomSample:(数字字母随机).
     * 
     * @author 作者(TODO)
     * @param offset
     * @return
     */
    private static String getRandomSample(int offset) {
        return getRandomCode(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }, offset);
    }

    /**
     * 
     * getRandomCode:(获取随机码).
     * 
     * @author 作者(TODO)
     * @param randoms
     *            随机数组
     * @param offset
     *            偏移量
     * @return
     */
    private static String getRandomCode(String[] randoms, int offset) {
        List<String> list = Arrays.asList(randoms);
        int size = list.size();
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        int m = 0, n = 0;
        do {
            m = (int) Math.floor(Math.random() * size);
            n = m + offset;
        } while (n >= size);
        String result = afterShuffle.substring(m, n);
        return result;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println(getRandomStr(10));
        }

    }

}
