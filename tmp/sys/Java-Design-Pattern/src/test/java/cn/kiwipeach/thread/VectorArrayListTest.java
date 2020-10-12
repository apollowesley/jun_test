/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Create Date: 2017/11/19 
 * Description: 线程安全测试
 * @author kiwipeach [1099501218@qq.com]
 */
public class VectorArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        //测试ArrayList线程安全
        List<String> arrayList = new ArrayList<>();
        SecurityTask securityTask = new SecurityTask(arrayList);
        for (int i=0;i<10;i++){
            new Thread(securityTask, "arrayList").start();
        }
        Thread.sleep(10000);
        System.out.println("Vector List Size Is "+securityTask.getTagetList().size());
        /**
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:正在添加数据....
         arrayList:正在添加数据....
         arrayList:当前数据==>[]
         arrayList:当前数据==>[]
         Vector List Size Is 8
         */

        //测试Vector是否线程安全
/*
        List<String> vectorList = new Vector<String>();
        SecurityTask securityTask = new SecurityTask(vectorList);
        for (int i=0;i<10;i++){
            new Thread(securityTask, "vectorList").start();
        }
        Thread.sleep(10000);
        System.out.println("Vector List Size Is "+securityTask.getTagetList().size());
*/
        /**
         vectorList:正在添加数据....
         vectorList:正在添加数据....
         vectorList:正在添加数据....
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         vectorList:当前数据==>[]
         vectorList:当前数据==>[]
         vectorList:当前数据==>[]
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         vectorList:正在添加数据....
         vectorList:当前数据==>[]
         Vector List Size Is 10
         */
    }
}
