import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/27  16:11
 * @描述 682. 棒球比赛       https://leetcode-cn.com/problems/baseball-game/
 */
public class LeetCode_682 {

    public static void main(String[] args) {
        System.out.println(new LeetCode_682().calPoints(new String[]{"5", "2", "C", "D", "+"}));
        System.out.println(new LeetCode_682().calPoints(new String[]{"5", "-2", "4", "C", "D", "9", "+", "+"}));
    }

    /** Method 1
     * Created by LuoXiang on 2019/09/27 16:28
     * Desc:   思路 ——   利用栈来保存每轮的操作。
     * 复杂度：   时间复杂度：O(n)  ;   空间复杂度：O(n)
     **/
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        for (String op : ops) {
            switch (op) {
                case "+":
                    Integer last = stack.pop();
                    Integer lastTwo = stack.peek();
                    stack.push(last);
                    stack.push(last + lastTwo);
                    break;
                case "D":
                    stack.push(stack.peek() * 2);
                    break;
                case "C":
                    stack.pop();
                    break;
                default:
                    stack.push(Integer.valueOf(op));
            }
        }
        int sum = 0;
        while (!stack.empty()) {
            sum += stack.pop();
        }
        return sum;
    }
}
