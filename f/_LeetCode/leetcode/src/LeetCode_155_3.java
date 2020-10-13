import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/26  20:08
 * @描述
 */
public class LeetCode_155_3 {

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
            }
        }

        public void pop() {

        }

        public int top() {
            return 0;
        }

        public int getMin() {
            return 0;
        }
    }
}
