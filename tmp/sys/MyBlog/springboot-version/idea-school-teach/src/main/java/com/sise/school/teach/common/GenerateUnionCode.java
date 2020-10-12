package com.sise.school.teach.common;

import java.util.Random;

/**
 * @author idea
 * @data 2018/11/18
 */
public class GenerateUnionCode {

    /**
     * 生成随机乱码字符串
     *
     * @param length
     * @return
     */
    private static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static String generateUnionCode(String prefix) {
        return prefix+getStringRandom(10);
    }

    public static void main(String[] args) {
        System.out.println(generateUnionCode("sms_code_"));
    }

}
