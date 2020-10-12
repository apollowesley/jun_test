package com.jfast.web.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zengjintao
 * @version 1.0
 * @create 2018/8/29 17:22
 **/
public class NumberUtils {

    private static final Logger logger = LoggerFactory.getLogger(NumberUtils.class);

    /**
     * 生成指定位数的随机字符串
     * @param length
     * @return
     */
    public static String getNumberCode(int length) {
        String number = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(number.length());
            sb.append(number.charAt(num));
            number = number.replace((number.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    /**
     * 获取16位随机字符串
     * @return String
     */
    public static String getUUID16()
    {
        String uuid = UUID.randomUUID().toString();
        char[] cs = new char[32];
        char c = 0;
        for (int i = uuid.length() / 2,j = 1; i-->0; ) {
            if ((c = uuid.charAt(i))!= '-'){
                cs[j++] = c;
            }
        }
        String uid = String.valueOf(cs);
        return uid;
    }

    /**
     * 生成邀请码
     * @param length
     * @return
     */
    public static String generateInviteCode(int length) {
        String number = "1234567890ABCDEFGHIJKMNXYZW";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(number.length());
            sb.append(number.charAt(num));
            number = number.replace((number.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    /**
     * 随机生成指定length的16进制数
     * @param length
     * @return
     */
    public static String randomHexString(int length) {
        try {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < length; i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            logger.error("生成随机数异常", e);
        }
        return null;
    }

    /**
     * 保留两位小数
     * @param number
     * @return
     */
    public static double dobuleToBigDecimal(double number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isInt(String value) {
        Pattern pattern = Pattern.compile("\\d{1,2}");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static double division(int divisor, int dividend) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String num = df.format((float)divisor / dividend);//返回的是String类型
        return Double.valueOf(num);
    }

    static List<String> letter = new ArrayList<String>();

    /**
     * 根据 number 获取英文字母
     * @param number
     * @return
     */
    public static String generateLetter(int number) {
        if (letter.size() == 0) {
            for (int i = 1; i <= 26; i++) {
                letter.add(ObjectUtils.totoUpperCaseFirst(String.valueOf((char)(96 + i))));
            }
        }
        return letter.get(number);
    }
}
