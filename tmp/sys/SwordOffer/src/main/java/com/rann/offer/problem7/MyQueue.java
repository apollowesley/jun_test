package com.rann.offer.problem7;

import java.util.Stack;

/**
 * Problem7
 * 两个栈实现队列
 *
 * @author lemonjing
 * @version 1.0
 */
public class MyQueue {

    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    public void appendTail(Integer a) {
        stack1.push(a);
    }

    public Integer deleteHead() throws Exception {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }

        if (stack2.isEmpty()) {
            throw new Exception("队列为空，不能删除");
        }

        return stack2.pop();
    }
}
