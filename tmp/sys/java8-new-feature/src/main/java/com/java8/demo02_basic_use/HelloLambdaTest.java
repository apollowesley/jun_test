package com.java8.demo02_basic_use;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liuburu on 2017/6/8.
 */
public class HelloLambdaTest {

    /**
     * 测试1：传统比较字符串方式
     */
    @Test
    public void test1() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });
        System.out.println(names);
    }

    /**
     * 测试2：使用Lambda表达式
     */
    @Test
    public void test2() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        //省略方法体“{}”,省略return关键字
        Collections.sort(names, (String a, String b) -> a.compareTo(b));
        /*方法三*/
        Collections.sort(names, (a, b) -> a.compareTo(b));
        System.out.println(names);
    }

    /**
     * 测试3：使用Lambda表达式
     */
    @Test
    public void test3() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        //省略方法体“{}”,省略return关键字,省略参数类型,Java编译器能自动识别参数类型
        Collections.sort(names, (a, b) -> a.compareTo(b));
        System.out.println(names);
    }

    /**
     * 拓展训练: 使用Lambda表达式，灵活的替代内部类
     */
    @FunctionalInterface
    interface Bird {
        void speak(String word);
    }

    class Parrot implements Bird {
        @Override
        public void speak(String word) {
            System.out.println("鹦鹉:" + word);
        }
    }

    class Person {
        private String word;

        public Person(String word) {
            this.word = word;
        }

        public void birdSay(Bird bird) {
            bird.speak(word);
        }
    }

    @Test
    public void test4() {
        Person person = new Person("hello java8 lambda");
        //1.传统方式
        person.birdSay(new Bird() {
            @Override
            public void speak(String word) {
                System.out.println("黄鹂鸟:" + word);
            }
        });
        //console result is:黄鹂鸟:hello java8 lambda
        //2.java8中的lambda表达式
        person.birdSay((word) -> {
            System.out.println("白鹭:" + word);
        });
        //console result is:白鹭:hello java8 lambda
    }
}
