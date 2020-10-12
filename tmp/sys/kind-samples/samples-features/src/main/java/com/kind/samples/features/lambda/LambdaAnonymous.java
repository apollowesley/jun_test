package com.kind.samples.features.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * JKD7匿名内部类与JDKLambda表达式写法
 * Created by weiguo.liu on 2016/10/18.
 */
public class LambdaAnonymous {
    /**
     * JDK7 匿名内部类写法
     */
    public void anonymousStyle() {
        List<String> names = Arrays.asList("cary", "jane", "jerry");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (String name : names) {
            System.out.println("name:" + name);
        }
    }

    /**
     * JDK8 Lambda表达式写法
     */
    public void lambdaStyle() {
        List<String> names = Arrays.asList("cary", "jane", "jerry");
        Collections.sort(names, (o1, o2) -> {
            if (o1 == null)
                return -1;
            if (o1 == null)
                return 1;
            return o1.length() - o2.length();
        });
    }

    class Anonymous {
        public void anonymousStyle() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Anonymous style thread run  ***************");
                }
            }).start();
        }
    }

    class Lambda {
        public void lambdaStyle() {
            new Thread(() -> System.out.println("Lambda Style thread run  ******************")).start();
            new Thread(() -> {
                System.out.println("lambda thread1 run********");
                System.out.println("lambda thread2 run********");
            }).start();
        }
    }


    public static void main(String[] args) {
        new LambdaAnonymous().anonymousStyle();
        new LambdaAnonymous().lambdaStyle();
        new LambdaAnonymous().new Anonymous().anonymousStyle();
        new LambdaAnonymous().new Lambda().lambdaStyle();

    }
}
