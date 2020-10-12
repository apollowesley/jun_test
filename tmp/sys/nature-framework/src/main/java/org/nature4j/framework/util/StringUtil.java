package org.nature4j.framework.util;

/**
 * 字符串工具类
 * Created by Ocean on 2016/3/8.
 */
public class StringUtil {

    /**
     * 判断不为空并且不为空字符串
     */
    public static boolean isNotEmpty(String string) {
        return string != null && string.length() != 0;
    }
    
    /**
     * 判断字符串为空或者空字符串
     */
    public static boolean isEmpty(String string) {
        return !isNotEmpty(string);
    }

    /**
     * 判断字符串不为空并且不为“空格+”
     */
    public static boolean isNotBank(String string) {
        return string != null && string.trim().length() != 0;
    }

    /**
     * 判断字符串为空或者为“空格+”
     */
    public static boolean isBank(String string) {
        return !isNotBank(string);
    }
}
