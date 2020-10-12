/*
 * Copyright 2018 KiWiPeach.
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
package com.java8.demo04_lambda_scope;

import org.junit.Test;

/**
 * Create Date: 2018/04/09
 * Description: lambda作用域范围测试
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class LambdaScopeTest {

    @FunctionalInterface
    interface IConverter<FROM_A, TO_B> {
        TO_B stringConverter(FROM_A source);
    }

    /**
     * 测试1：访问局部变量
     */
    @Test
    public void test1() {
        String authorInfo = "作者:kiwipeach";
        String param = " Hello Java8";
        IConverter<String, String> converter = (source) -> {
            //会自动的把authorInfo识别为final变量
            return String.valueOf(authorInfo + source);
        };
        //访问局部变量
        String result = converter.stringConverter(authorInfo + param);
        //非法操作：企图改变final变量的值
        //authorInfo = "XXXXXX";
        System.out.println(result);
    }

    /**
     * 测试2：访问静态变量和成员变量
     */
    interface IStudentFactory<T extends Student> {
        T generate(String name);
    }

    class Student {
        private String name;
        private static final String INFO = "Student==>";

        public Student(String name) {
            this.name = name;
        }

        public String echo(String word) {
            IConverter<String, String> iConverter = (param) -> {
                //访问成员静态变量、成员变量
                String result = INFO + "name:" + name + " say " + param;
                return result;
            };
            return iConverter.stringConverter(word);
        }
    }

    @Test
    public void test2() {
        IStudentFactory<Student> iStudentFactory = Student::new;
        Student kiWiPeach = iStudentFactory.generate("KiWiPeach");
        String echo = kiWiPeach.echo("你好Java8");
        System.out.println(echo);
    }

    /**
     * 测试3：访问接口的静态方法和default方法
     */
    @FunctionalInterface
    interface IMessage {
        void send(String message);

        default void logBegin(Object obj) {
            System.out.println("日志开始:" + obj);
        }

        static void logEnd(Object obj) {
            System.out.println("日志结束:" + obj);
        }
    }

    /**
     * 测试3：无法像普通的class一样去访问lambda接口中的默认成员函数和静态函数
     */
    @Test
    public void test3() {
        String message = "我是一条消息";
        IMessage iMessage = (msg) -> {
            //default方法，无法访问
            //logBegin(msg);
            System.out.println("发送消息:" + msg);
            //static方法，无法访问
            //logEnd(msg);
        };
        iMessage.send(message);
    }
}
