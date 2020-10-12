package com.rann.offer.problem7;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 *
 * @author lemonjing
 * @version 1.0
 */
public class MyStack {
    private Queue<Integer> q1 = new LinkedList<>();
    private Queue<Integer> q2 = new LinkedList<>();

    public Integer push(Integer a) {
        if (q1.isEmpty() && !q2.isEmpty()) {
            q2.offer(a);
        }
        if (!q1.isEmpty() && q2.isEmpty()) {
            q1.offer(a);
        }
        //都空时从q1开始
        if (q1.isEmpty() && q2.isEmpty()) {
            q1.offer(a);
        }
        return a;
    }

    public Integer pop() throws Exception {
        Integer res = null;
        if (q1.isEmpty() && q2.isEmpty()) {
            throw new Exception("Error,栈为空");
        }

        if (q1.isEmpty() && !q2.isEmpty()) {
            int i = q2.size();
            while (i > 1) {
                q1.offer(q2.poll());
                i--;
            }
            res = q2.poll();
        } else if (!q1.isEmpty() && q2.isEmpty()) {
            int i = q1.size();
            while (i > 1) {
                q2.offer(q1.poll());
                i--;
            }
            res = q1.poll();
        }

        return res;
    }
}
