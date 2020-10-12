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
package com.java8.demo03_function_ref;

import org.junit.Test;

/**
 * Create Date: 2018/03/09
 * Description: 测试LambdaTest基本概念，“函数式接口”
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class LambdaTest {


    /**
     * 测试1：当添加了@FunctionalInterface注解时，一个所谓的函数式接口必须要有且仅有一个抽象方法声明，
     * 无此注解则不会检查。
     */
    @FunctionalInterface
    interface MyConverter1<S, N> {
        N convert(S from);
        //void convert(String from);  @FunctionalInterface存在时将报错
    }

    @Test
    public void test1() {
        //整型-->字符型
        MyConverter1<String, Integer> integerConverter = (param) -> Integer.valueOf(param);
        Integer targetInteger = integerConverter.convert("123");
        System.out.println(targetInteger instanceof Integer);  //ture
        //字符型-->整型
        MyConverter1<Integer, String> stringConverter = (param) -> String.valueOf(param);
        String targetString = stringConverter.convert(123);
        System.out.println(targetString instanceof String);
    }


    /**
     * 测试2：由于默认方法不是抽象的，因此你可以在你的函数式接口里任意添加默认方法
     */
    @FunctionalInterface
    interface MyConverter2<S, N> {
        N convert(S from);

        default int sqrt(int a) {
            return (int) Math.sqrt(a);
        }

        default int plus(int a, int b) {
            return a + b;
        }
    }

    @Test
    public void test2() {
        MyConverter2<String, Double> integerConverter = (param) -> Double.valueOf(param);
        Double targetInteger = integerConverter.convert("123");
        System.out.println(targetInteger instanceof Double);                 //ture
        System.out.println(integerConverter.convert("66.6"));         // 66.6
        System.out.println(integerConverter.sqrt(64));                    //8
        System.out.println(integerConverter.plus(1, 2));                //3
    }


    /**
     * 测试3：指向Java8实体类的静态函数的Lambda接口
     */
    @FunctionalInterface
    interface StaticMethodPointer {
        /**
         * 指向类的静态方法
         *
         * @param version 版本
         * @return 返回版本信息
         */
        String sayJava8Version(String version);
        //注意：不能有多个抽象方法，所以不能再这里定义函数指向构造和setter/getter了
    }

    @Test
    public void test3() {
        //StaticMethodPointer methodPointer = (version) -> Java8.sayVersion(version);
        StaticMethodPointer methodPointer = Java8::sayVersion;
        //StaticMethodPointer methodPointer = String::valueOf;
        String resultVersion = methodPointer.sayJava8Version("1.8");
        System.out.println(resultVersion);      //Java8{version='1.8'}
    }

    /**
     * 测试4：指向实体类的成员函数的Lambda接口
     */
    @FunctionalInterface
    interface InstanceMethodPointer {
        void setJava8Version(String version);
    }

    @Test
    public void test4() {
        Java8 java8 = new Java8("1.8");
        InstanceMethodPointer methodPointer = java8::setVersion;
        methodPointer.setJava8Version("1.9");
        System.out.println(java8.getVersion()); //1.9
    }

    /**
     * 测试5：指向实体类的构造函数的Lambda接口
     */
    @FunctionalInterface
    interface ContructorPointer {
        Java8 create(String version);
    }

    @Test
    public void test6() {
        ContructorPointer contructorPointer = Java8::new;
        Java8 java8 = contructorPointer.create("1.8");
        String version = java8.getVersion();
        System.out.println(version);    //1.8
    }


    /**
     * 拓展训练：使用java8的lambada表达式实例化一个对象并且对该对象进行setter/getter并且调用静态方法
     */
    interface IStudent<T extends Student> {
        T generate();
    }

    interface Setter {
        void setAttribute(String name);
    }

    interface Getter {
        String getAttribute();
    }

    interface IStaticMethod {
        void staticMethodInvoke(Student student);
    }

    @Test
    public void test7() {
        //构造lambda
        IStudent<Student> iStudent = Student::new;
        Student student = iStudent.generate();
        //访问器lambda
        Setter setter = student::setName;
        Getter getter = student::getName;
        setter.setAttribute("kiwipeach");
        System.out.println("访问器:" + getter.getAttribute());
        //静态方法lambada
        IStaticMethod iStaticMethod = Student::staticMethod;
        iStaticMethod.staticMethodInvoke(student);
    }
}
