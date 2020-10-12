package com.ilvyou.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GuanYuCai on 2016/9/7 0007.
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    public static String base64Decode(String str) {
        try{
            StringBuffer sb = new StringBuffer();
            byte[] data = str.getBytes("US-ASCII");
            int len = data.length;
            int i = 0;
            int b1, b2, b3, b4;
            while (i < len) {
                do {
                    b1 = base64DecodeChars[data[i++]];
                } while (i < len && b1 == -1);
                if (b1 == -1) {
                    break;
                }

                do {
                    b2 = base64DecodeChars[data[i++]];
                } while (i < len && b2 == -1);
                if (b2 == -1) {
                    break;
                }
                sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

                do {
                    b3 = data[i++];
                    if (b3 == 61) {
                        return sb.toString();
                    }
                    b3 = base64DecodeChars[b3];
                } while (i < len && b3 == -1);
                if (b3 == -1) {
                    break;
                }
                sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

                do {
                    b4 = data[i++];
                    if (b4 == 61) {
                        return sb.toString();   //iso8859-1
                    }
                    b4 = base64DecodeChars[b4];
                } while (i < len && b4 == -1);
                if (b4 == -1) {
                    break;
                }
                sb.append((char) (((b3 & 0x03) << 6) | b4));
            }
            return sb.toString();
        }catch (Exception e){
            Logger log = Logger.getLogger(StringUtil.class);
            log.debug("base64 decode exception: " + str);
            return "";
        }
    }

    private static byte[] base64DecodeChars = new byte[]{
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
            -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
    };

    public static boolean isMobile(String mobile){
        return Pattern.compile("^1\\d{10}$").matcher(mobile).matches();
    }

    public static boolean isBankCard(String card){
//		String format = "^(\d{16}|\d{17}|\d{18}|\d{19})$";  //正则表达式，验证银行卡号是否合法
        String format = "^([0-9]{16,19})$";  //正则表达式，验证银行卡号是否合法
        Matcher m = Pattern.compile(format).matcher(card);
        return m.matches();
    }

    public static String randomNum(int length){
        String str = "";
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            str += random.nextInt(10);
        }

        return str;
    }

    public static String UUID() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static String toCurrency(Double var) {
        if (var == null){
            return "0.00";
        }

        DecimalFormat format = new DecimalFormat("0.00");
        String str = format.format(var);
        return "-0.00".equals(str) ? "0.00" : str;
    }

    public static String toCurrency(String var) {
        if (isEmpty(var)){
            return "0.00";
        }
        double f = Double.parseDouble(var);
        DecimalFormat format = new DecimalFormat("0.00");
        String str = format.format(f);
        return "-0.00".equals(str) ? "0.00" : str;
    }
}
