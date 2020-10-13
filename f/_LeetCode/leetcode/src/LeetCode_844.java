import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/27  10:25
 * @描述 844. 比较含退格的字符串          https://leetcode-cn.com/problems/backspace-string-compare/
 */
public class LeetCode_844 {
    public static void main(String[] args) {
        System.out.println(new LeetCode_844().backspaceCompare2("ab#c", "ad#c"));
        System.out.println(new LeetCode_844().backspaceCompare2("ab##", "c#d#"));
        System.out.println(new LeetCode_844().backspaceCompare2("a#c", "b"));
    }

    /**
     * Created by LuoXiang on 2019/09/27 10:48
     * Desc:   思路 ——  基于 StringBuilder 的字符串的拼接和删减
     * 复杂度：     时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public boolean backspaceCompare2(String S, String T) {
        StringBuilder builderS = new StringBuilder();
        StringBuilder builderT = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c != '#') {
                builderS.append(c);
            } else if (builderS.length() > 0) {
                builderS.deleteCharAt(builderS.length() - 1);
            }
        }
        for (int i = 0; i < T.length(); i++) {
            char c = T.charAt(i);
            if (c != '#') {
                builderT.append(c);
            } else if (builderT.length() > 0) {
                builderT.deleteCharAt(builderT.length() - 1);
            }
        }
        return builderS.toString().equals(builderT.toString());
    }


    /**
     * Created by LuoXiang on 2019/09/27 10:48
     * Desc:   思路 —— 基于两个栈的实现
     * 复杂度：     时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public boolean backspaceCompare(String S, String T) {
        Stack<Character> stackOne = new Stack<>();
        Stack<Character> stackTwo = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '#') {
                if (!stackOne.empty()) stackOne.pop();
            } else {
                stackOne.push(c);
            }
        }
        for (int i = 0; i < T.length(); i++) {
            char c = T.charAt(i);
            if (c == '#') {
                if (!stackTwo.empty()) stackTwo.pop();
            } else {
                stackTwo.push(c);
            }
        }
        if (stackOne.size() != stackTwo.size()) {
            return false;
        }
        while (!stackOne.empty()) {
            if (!stackOne.pop().equals(stackTwo.pop())) {
                return false;
            }
        }
        return true;
    }
}
