package com.slavic.vesna.leet.leet;

import java.util.Deque;
import java.util.LinkedList;

public class Leet0020 {

    // 方法一  递归删除括号对，直到不包含括号对
    public boolean isValid(String s) {
        return replace(s).length() == 0;
    }

    private String replace(String s) {
        if (!s.contains("()") && !s.contains("{}") && !s.contains("[]")) {
            return s;
        }
        return s.replaceAll("\\(\\)", "").replaceAll("\\{\\}", "").replaceAll("\\[\\]", "");
    }

    //方法二  用栈保存左括号对应的右括号
    public boolean isValid2(String s) {
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') deque.offerFirst(')');
            else if (s.charAt(i) == '[') deque.offerFirst(']');
            else if (s.charAt(i) == '{') deque.offerFirst('}');
            else if (deque.isEmpty() || s.charAt(i) != deque.pollFirst()) return false;
        }
        return deque.isEmpty();
    }

}
