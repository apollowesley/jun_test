package com.rann.offer.problem21;

import java.util.Stack;

/**
 * Problem21
 * 得到栈的最小元素
 */
public class StackWithMin {
    private Stack<Integer> dataStack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    public void push(Integer item) {
        dataStack.push(item);
        if (minStack.size() == 0 || item <= minStack.peek()) {
            minStack.push(item);
        } else {
            minStack.push(minStack.peek());
        }
    }

    public Integer pop() {
        minStack.pop();
        return dataStack.pop();
    }

    public Integer min() {
        return minStack.peek();
    }
}
