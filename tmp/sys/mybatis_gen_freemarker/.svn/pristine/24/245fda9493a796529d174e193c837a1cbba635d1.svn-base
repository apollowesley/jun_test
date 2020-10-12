package com.itmuch.gen.util;

import java.io.IOException;
import java.util.Properties;

public class StringUtil {
    /**
     * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：helloWorld->hello_world
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线小写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if ((name != null) && (name.length() > 0)) {
            // 将第一个字符处理成小写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在小写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成小写
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * 将下划线小写方式命名的字符串转换为驼峰式。如果转换前的下划线小写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->helloWorld
     * @param name 转换前的下划线小写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if ((name == null) || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母小写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    private static final String PLACEHOLDER_START = "${";

    /**
     * 解析字符串中的占位符
     * @param string
     * @return
     * @throws IOException 
     */
    public static String resolvePlaceHolder(String string, Properties prop) throws IOException {
        if (string.indexOf(PLACEHOLDER_START) < 0) {
            return string;
        }
        StringBuffer buff = new StringBuffer();
        char[] chars = string.toCharArray();
        for (int pos = 0; pos < chars.length; pos++) {
            if (chars[pos] == '$') {
                // peek ahead
                if (chars[pos + 1] == '{') {
                    // we have a placeholder, spin forward till we find the end
                    String key = "";
                    int x = pos + 2;
                    for (; (x < chars.length) && (chars[x] != '}'); x++) {
                        key += chars[x];
                        // if we reach the end of the string w/o finding the
                        // matching end, that is an exception
                        if (x == (chars.length - 1)) {
                            throw new IllegalArgumentException("unmatched placeholder start [" + string + "]");
                        }
                    }
                    String value = (String) prop.get(key);
                    buff.append(value == null ? "" : value);
                    pos = x + 1;
                    // make sure spinning forward did not put us past the end of the buffer...
                    if (pos >= chars.length) {
                        break;
                    }
                }
            }
            buff.append(chars[pos]);
        }
        String rtn = buff.toString();
        return isEmpty(rtn) ? null : rtn;
    }

    /**
    * 判断字符串的空(null或者.length=0)
    * @param string
    * @return
    */
    public static boolean isEmpty(String string) {
        return (string == null) || (string.length() == 0);
    }

    /**
     * 表名转类名
     * @param tableName
     * @param prefix
     * @return
     */
    public static String convertTableNameToClzName(String tableName, String prefix) {
        String tableNameNoPrefix = tableName.replaceAll(prefix, "");
        String camelName = StringUtil.camelName(tableNameNoPrefix);
        return (new StringBuilder()).append(Character.toUpperCase(camelName.charAt(0))).append(camelName.substring(1)).toString();

    }
}
