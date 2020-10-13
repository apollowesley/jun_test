package cn.jeeweb.beetl.tags.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     *
     * @title: camelToUnderline
     * @description: 驼峰转下划线
     * @param param
     * @return
     * @return: String
     */
    public static String camelToStrikethrough(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('-');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @title: underlineToCamel
     * @description:下划线转驼峰
     * @param param
     * @return
     * @return: String
     */
    public static String strikethroughToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
