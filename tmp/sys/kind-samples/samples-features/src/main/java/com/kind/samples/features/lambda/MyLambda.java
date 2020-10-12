package com.kind.samples.features.lambda;

import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;

/**
 * Created by weiguo.liu on 2016/10/21.
 */
public class MyLambda {

    public void lambda() {
        Runnable run = () -> System.out.println("Thread run********");

        ActionListener listener = event -> System.out.println("button clicked");
        /**
         * 代码块
         */
        Runnable block = () -> {

            System.out.println("Lambda 代码块******");
            System.out.println("Lambda 代码块******");
        };
        BinaryOperator<Long> add = (Long x, Long y) -> x + y;
        /**
         * 5 类型推断
         */
        BinaryOperator<Long> infer = (x, y) -> x + y;//
    }

    public static void main(String[] args) {
        new MyLambda().lambda();
    }
}
