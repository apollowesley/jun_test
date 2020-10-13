import java.util.Stack;

/**
 * @创建人 luoxiang
 * @创建时间 2019/9/26  19:55
 * @描述      155. 最小栈        https://leetcode-cn.com/problems/min-stack/
 */
public class LeetCode_155_2 {
    class MinStack {

        Stack<Integer> data;
        Stack<Integer> minStack;

        /** initialize your data structure here. */
        public MinStack() {
            data=new Stack();
            minStack=new Stack();
        }

        public void push(int x) {
           data.push(x);
           if(minStack.empty() || minStack.peek()>=x){
               minStack.push(x);
           }
        }

        public void pop() {
            if(!minStack.empty() && minStack.peek().equals(data.pop())){
                minStack.pop();
            }
        }

        public int top() {
            return data.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
