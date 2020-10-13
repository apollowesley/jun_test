import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/27  10:11
 * @描述 155. 最小栈        https://leetcode-cn.com/problems/min-stack/
 */
public class LeetCode_155_4 {

    class MinStack {

        int min = Integer.MAX_VALUE;
        Stack<Integer> stack;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            stack = new Stack<>();
        }

        public void push(int x) {
            if (x <= min) {
                min = x;
                stack.push(min);
            }
            stack.push(x);
        }

        public void pop() {
            if (stack.pop() == min) {
                min = stack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }
}
