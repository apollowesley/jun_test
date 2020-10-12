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

import java.util.List;

/**
 * Create Date: 2017/11/19 
 * Description: 线程安全测试任务
 * @author kiwipeach [1099501218@qq.com]
 */
public class SecurityTask implements Runnable {

    private List<String> tagetList;

    public SecurityTask(List<String> tagetList) {
        this.tagetList = tagetList;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+":正在添加数据....");
        try {
            System.out.println(Thread.currentThread().getName()+":当前数据==>"+tagetList);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tagetList.add(Thread.currentThread().getName()+":"+Math.random());
    }

    public List<String> getTagetList() {
        return tagetList;
    }

    public void setTagetList(List<String> tagetList) {
        this.tagetList = tagetList;
    }
}
