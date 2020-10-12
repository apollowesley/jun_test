package com.rann.offer.problem4;

/**
 * Problem4
 * 替换空格
 *
 * @author lemonjing
 */
public class ReplaceBlank {
    public String replaceBlank(String input) {
        if (null == input || input.length() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                sb.append('%');
                sb.append('2');
                sb.append('0');
            } else {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }

    public String replaceByRegex(String input) {
        if (null == input) {
            return null;
        }
        return input.replace(" ", "%20");
    }
}
