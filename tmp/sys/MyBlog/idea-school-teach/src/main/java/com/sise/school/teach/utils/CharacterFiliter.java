package com.sise.school.teach.utils;

/**
 * 字符串过滤器
 *
 * @author idea
 * @data 2018/10/20
 */
public class CharacterFiliter {

    public static String filiter(String content) {
        content = content.replace("\\r", "");
        content = content.replace("\\n", "");
        content = content.replace("\\t", "");
        content = content.replace("\r", "");
        content = content.replace("\n", "");
        content = content.replace("\t", "");
        return content;
    }

    public static void main(String[] args) {
        System.out.println(filiter("shdjkhjad\\r\\n\\tasdasdasd\\rsddsad\\nsdfsdf\\t"));
    }

}
