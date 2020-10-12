/**  
 * @Project RTMS
 * @Title MD5Util.java
 * @Package com.bhe.util.security
 * @Description TODO
 * @author tuxy
 * @date 2013-6-8 下午1:51:12
 * @Copyright ©2013Chongqing Rainbow Technology Co., Ltd. All Rights Reserved.
 * @version V1.0  
 */
package com.qxzl.util;

import java.security.MessageDigest;

/**
 * @ClassName MD5Util
 * @Description MD5加密，调用jdk方法
 * @author tuxy
 * @date 2013-6-8 下午1:51:12
 */
public class MD5Util {
	public final static String MD5(String s) {
		if(s == null) throw new IllegalArgumentException("参数不能为null");
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
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
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println(MD5Util.MD5("20121221"));
        System.out.println(MD5Util.MD5("加密"));
    }

}
