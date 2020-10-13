/**
 * @创建人 luoxiang
 * @创建时间 2019/9/30  15:46
 * @描述 1021. 删除最外层的括号      https://leetcode-cn.com/problems/remove-outermost-parentheses/
 */
public class LeetCode_1021 {
    public static void main(String[] args) {
        System.out.println(new LeetCode_1021().removeOuterParentheses("(()())(())"));
        System.out.println(new LeetCode_1021().removeOuterParentheses("(()())(())(()(()))"));
        System.out.println(new LeetCode_1021().removeOuterParentheses("()()"));
    }

    /**
     * Method 1
     * Created by LuoXiang on 2019/09/30 15:51
     * Desc:   思路  —— 利用一个计数器来判断括号的层级关系。 count 表示有多少层括号
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public String removeOuterParentheses(String S) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                count++;
                if (count > 1) builder.append(c);
            } else {
                count--;
                if (count != 0) builder.append(c);
            }
        }
        return builder.toString();
    }
}
