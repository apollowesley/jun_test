package com.qxzl.common.util;

// str.matches() 匹配
// str.replaceAll(regex, replacement) 替换
// Pattern compile = Pattern.compile(regex);提取
// Matcher m = compile.matcher(str);
// str.split(regex) 分割

/**
 * 正则表达式工具
 */

public class RegexTool {
    /**
     * 匹配是否是手机号码
     */
    public static boolean isPhoneNum(String phoneNum) {
        return phoneNum.matches("1[34578][\\d]{9}");
    }

    /**
     * 匹配包含汉字
     */
    public static boolean isHaveChinese(String msg) {
        return msg.matches(".*[\\u4e00-\\u9FA5].*");
    }
}
