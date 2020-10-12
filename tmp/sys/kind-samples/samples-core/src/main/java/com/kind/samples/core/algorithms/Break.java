/**
 * Project Name:java-core
 * Package Name:com.kind.core.algorithms
 * Created on:2016年9月11日下午11:30:02
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.samples.core.algorithms;

/**
 * Function:跳出循环测试. <br/>
 *
 * @author weiguo.liu
 * @see
 * @since JDK 1.7
 */
public class Break {
    /**
     * 演示默认情况下的break跳出循环，是最里层循环。
     */
    public void cycle1() {
        for (int i = 0; i < 5; i++) {
            System.out.println("I 的值是：" + i);
            for (int j = 0; j < 4; j++) {
                System.out.println("J 的值是：" + j);
                for (int m = 0; m < 3; m++) {
                    if (m == 2) {
                        System.out.println("M 的值是：" + m + " 跳出最里层循环");
                        break;
                    } else {
                        System.out.println("M 的值是：" + m);
                    }
                }
            }
        }
    }

    /**
     * 这里演示break跳出指定层循环。
     */
    public void cycle2() {
        lableA:
        // 这里就是循环的标签
        for (int i = 0; i < 5; i++) {
            System.out.println("I 的值是：" + i);
            lableB:
            // 这里就是循环的标签
            for (int j = 0; j < 4; j++) {
                System.out.println("J 的值是：" + j);
                for (int m = 0; m < 3; m++) {
                    if (m == 1) {
                        System.out.println("M 的值是：" + m + " 跳出第二层循环");
                        break lableB;
                    } else if (m == 2) {
                        System.out.println("M 的值是：" + m + " 跳出最外层循环");
                        break lableA;
                    } else {
                        System.out.println("M 的值是：" + m);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Break b = new Break();
        b.cycle1();
        b.cycle2();
    }
}
