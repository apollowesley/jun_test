/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.composite;

/**
 * Create Date: 2017/11/19 
 * Description: 组合模式测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {
    public static void main(String[] args) {
        TreeNode A = new TreeNode("A");
        TreeNode B = new TreeNode("B");
        TreeNode C = new TreeNode("C");
        TreeNode D = new TreeNode("D");
        TreeNode E = new TreeNode("E");
        TreeNode F = new TreeNode("F");
        A.addNode(B);
        A.addNode(C);
        A.addNode(D);
        D.addNode(E);
        D.addNode(F);
        System.out.println(A);
        /**
         debug redsult is:
         *   A
         * B  C  D
         *       E F
         */

    }
}
