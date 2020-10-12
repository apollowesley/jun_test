package com.site.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 生成劵号
 * 
 * @Copyright 北京瑞友科技股份有限公司上海分公司-2014
 * @author wangxin
 * @Date May 16, 2015
 */
public class GenerateCode {

    public static String[] zm = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };// 替换流水规则

    public static void main(String[] args) {
        for (String code : generateCode("A", 10000)) {
            System.err.println(code);
        }
    }

    /**
     * 生成劵号
     * 
     * @param type 劵号类型
     * @param size
     * @return
     */
    public static List<String> generateCode(String type, int size) {
        StringBuilder key = null;
        String code = null;
        int b = 0, e = 0, zm_b = 0, zm_e = 0;
        Random rand = new Random();
        Map<String, String> map = new HashMap<String, String>();
        int zmLength = zm.length;
        while (map.size() < size) {
            b = rand.nextInt(8);
            e = rand.nextInt(8);

            zm_b = rand.nextInt(zmLength);
            zm_e = rand.nextInt(zmLength);

            key = new StringBuilder(toHex(Calendar.getInstance().getTimeInMillis()));
            key.insert(b, zm[zm_b]);
            key.insert(e, zm[zm_e]);
            code = key.toString();
            map.put(code, type + code);
        }
        return new ArrayList<String>(map.values());
    }

    /**
     * 获取8位不重复随机码（取当前时间戳转化为十六进制）
     * 
     * @author wangxin
     * @param time
     * @return
     */
    public static String toHex(long time) {
        return Integer.toHexString((int) time);
    }

}
