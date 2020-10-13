import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/26  10:42
 * @描述 20. 有效的括号       https://leetcode-cn.com/problems/valid-parentheses/
 */
public class LeetCode_20 {
    public static void main(String[] args) {
        System.out.println(new LeetCode_20().isValid2("()"));
        System.out.println(new LeetCode_20().isValid2("]"));
        System.out.println(new LeetCode_20().isValid2("()[]{}"));
        System.out.println(new LeetCode_20().isValid2("{[]}"));
        System.out.println(new LeetCode_20().isValid2("([)]"));
    }

    /** Method 2
     * Created by LuoXiang on 2019/09/26 11:40
     * Desc:  思路 —— 利用 哈希表 来处理。
     * 复杂度： 时间复杂度：O(n)  ;   空间复杂度： O(n)
     **/
    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                if (!stack.empty() && map.get(stack.peek()).equals(c)) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }


    /** Method 1
     * Created by LuoXiang on 2019/09/26 11:40
     * Desc:   思路 —— 字符串一一匹配。从栈中推出
     * 复杂度：  时间复杂度：O(n)   ;   空间复杂度：O(n)
     **/
    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : chars) {
            if (!stack.empty()) {
                if (c == ')' && stack.peek() == '(') {
                    stack.pop();
                } else if (c == ']' && stack.peek() == '[') {
                    stack.pop();
                } else if (c == '}' && stack.peek() == '{') {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            } else {
                stack.push(c);
            }
        }
        return stack.empty();
    }
}
