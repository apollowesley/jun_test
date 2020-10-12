package com.cdh.util;

import java.security.MessageDigest;

public class MD5Util {
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String getMD5(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception ignored) {
            System.out.println("MD5转换异常！！");
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("admin"));//21232f297a57a5a743894a0e4a801fc3
        System.out.println(MD5Util.getMD5("123")); //202cb962ac59075b964b07152d234b70
    }
}
