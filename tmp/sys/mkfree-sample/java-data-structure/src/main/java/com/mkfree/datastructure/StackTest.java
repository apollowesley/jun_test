package com.mkfree.datastructure;

import java.util.Stack;

public class StackTest {

    public static void main(String[] args) {
        String a = "{}{}{}{{}}";


        Stack<Character> stack = new Stack<>();


        for (int i = 0; i < a.toCharArray().length; i++) {
            char ss = a.toCharArray()[i];
            if ('{' != ss) {
                System.out.println(false);
                return;
            }

            if (stack.empty()) {
                continue;
            }

            char sss = stack.peek();
            if ('}' == sss) {
                stack.pop();
            } else {
                stack.push(ss);
            }

        }


    }
}
