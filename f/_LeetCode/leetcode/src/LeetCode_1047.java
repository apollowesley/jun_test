import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/30  15:18
 * @描述 1047. 删除字符串中的所有相邻重复项        https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/
 */
public class LeetCode_1047 {
    public static void main(String[] args) {
        System.out.println(new LeetCode_1047().removeDuplicates("abbaca"));
    }

    /**
     * Method 1
     * Created by LuoXiang on 2019/09/30 15:37      ;
     * Desc:   思路  —— 栈的思路，当前的这个字符 和 之前的一个字符相比，相等就移除（直接使用栈反而不好，栈输出的时候是反向输出的又需要做处理）
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public String removeDuplicates(String S) {
        StringBuilder builder = new StringBuilder().append(S.charAt(0));
        for (int i = 1; i < S.length(); i++) {
            char c = S.charAt(i);
            if (builder.length() > 0 && builder.charAt(builder.length() - 1) == c) {
                builder.deleteCharAt(builder.length() - 1);
            } else {
                builder.append(c);
            }
        }
        return builder.toString();

    }
}
