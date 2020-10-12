/*
 * Copyright 2019 liuburu@qq.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo;

import cn.hutool.system.RuntimeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：堆栈虚拟机参数测试
 *
 * @author kiwipeach
 * @create 2019-10-04
 */
public class OutOfMemoryMain {

    static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        //-Xms4m -Xmx64m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=outOfMemoryError_javaapplication.hprof
        //-Xms4m -Xmx64m -XX:+HeapDumpOnOutOfMemoryError
        //-Xms 物理堆小内存,系统的1/64 ==> 16G/4 = 256M
        //-Xmx 物理堆大内存,系统的1/4  ==> 16G/4 = 4G = 4096M
        //-XX:+HeapDumpOnOutOfMemoryError  出现内存溢出异常时会生成dump文件,默认位置本工程路径
        //-XX:HeapDumpPath=outOfMemoryError_javaapplication.hprof 定义内存溢出文件存放路径

        //堆内存溢出
        List<User> users = new ArrayList<>();
        while (true) {
            users.add(new User());
            //System.out.println(new RuntimeInfo().toString());
        }

        //栈溢出,不会生成文件
        //stackMethod();
    }

    public static void stackMethod() {
        stackMethod();
    }
}
