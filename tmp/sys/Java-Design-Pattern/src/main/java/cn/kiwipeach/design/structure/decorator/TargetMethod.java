/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.decorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/11/15 
 * Description: 计时的目标方法
 * @author kiwipeach [1099501218@qq.com]
 */
public class TargetMethod implements IMethod {

    private static List<Integer> list = new ArrayList<Integer>();

    public static List<Integer> getList() {
        return list;
    }

    public static void setList(List<Integer> list) {
        TargetMethod.list = list;
    }

    @Override
    public  void addData2List(){
        for (int i=0;i<10000000;i++){
            list.add(new Integer(i));
        }
    }
}
