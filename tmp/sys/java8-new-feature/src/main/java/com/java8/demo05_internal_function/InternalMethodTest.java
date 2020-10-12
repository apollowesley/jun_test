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
package com.java8.demo05_internal_function;

import org.junit.Test;

import java.util.*;
import java.util.function.*;

/**
 * Create Date: 2018/04/09
 * Description: java8内置函数测试
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class InternalMethodTest {
    class Person {
        private String name;
        private Integer age;

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * 测试1：测试Predicates函数（布尔类型函数）
     */
    @Test
    public void test1() {
        //长度判断
        String source = "foo";
        Predicate<String> lenPredicate = (s) -> s.length() > 2;
        // test为抽象方法;and,negate,or,isEqual
        //1.基础用法以及negate测试
        System.out.println("布尔判断?" + lenPredicate.test(source));//true
        System.out.println("非操作判断?" + lenPredicate.negate().test(source));//false
        Predicate<Person> nonNull = Objects::nonNull;
        System.out.println("指向Objects的Person类型非空判断?" + nonNull.test(new Person()));//true
        Predicate<String> isNull = Objects::isNull;
        System.out.println("指向Objects的Person类型为空判断?" + isNull.test(null));//true
        Predicate<String> isEmpty = String::isEmpty;
        System.out.println("指向String的String类型为空判断?" + isEmpty.test(""));//true
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println("指向String的String类型为空判断?" + isNotEmpty.test(""));//true
        //2.and，or，isEqual测试
        Predicate<String> andPredicate = lenPredicate.and(isNotEmpty);
        Predicate<String> orPredicate = lenPredicate.or(isEmpty).or(isNull);
        Person person = new Person();
        Predicate<Person> isEqualPredicate = Predicate.isEqual(person);
        boolean result1 = andPredicate.test("123456");//true && true
        boolean result2 = orPredicate.test("123456");//true || false|| false
        System.out.println("and 测试：" + result1);
        System.out.println("or 测试：" + result2);
        System.out.println("isEqual 测试：" + isEqualPredicate.test(person));//true
    }

    /**
     * 测试2：Functions函数, R apply(T t);
     */
    @Test
    public void test2() {
        //1.简单测试
        Function<String, Integer> string2IntegerFunction = (source) -> {
            int anInt = 0;
            try {
                anInt = Integer.parseInt(source);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return anInt;
        };
        Integer applyResult = string2IntegerFunction.apply("100");
        System.out.println(applyResult);
        Function<String, String> function1 = (source) -> "china-" + source + "-china";
        Function<String, String> function2 = (source) -> "ZH_CN-" + source + "-ZH_CN";

        //2.compose用法，先执行参数,default <V> Function<V, R> compose(Function<? super V, ? extends T> before)
        Function<String, String> compose12 = function2.compose(function1);
        String composeResult = compose12.apply("中国");
        System.out.println("composeResult=" + composeResult);

        //3.andThen用法，先执行调用者,default <V> Function<T, V> andThen(Function<? super R, ? extends V> after)
        Function<String, String> andThen12 = function1.compose(function2);
        String andThenResult = andThen12.apply("中国");
        System.out.println("andThenResult=" + andThenResult);

        //4.identity用法，static <T> Function<T, T> identity()
        Function<String, String> identity = Function.identity();
        String kiwipeach = identity.apply("kiwipeach");
        System.out.println(kiwipeach);
    }

    /**
     * 测试3：Suppliers生产者，没有入参，返回给定类型的结果.  T get();
     */
    @Test
    public void test3() {
        Supplier<Person> personSupplier = Person::new;
        System.out.println(personSupplier.get() == null); //false
    }

    /**
     * 测试4：Consumers测试,接受一个入参. void accept(T t);
     */
    @Test
    public void test4() {
        Person me = new Person("kiwipeach");
        // void accept(T t) 测试
        Consumer<Person> personConsumer = (person) -> {
            System.out.println("个人消费:" + person.getName());
        };
        personConsumer.accept(me);
        // default Consumer<T> andThen(Consumer<? super T> after) 测试
        Consumer<Person> restaurantConsumer = (person) -> {
            System.out.println("餐厅消费:" + person.getName());
        };
        Consumer<Person> taxiConsumer = (person) -> {
            System.out.println("租车消费:" + person.getName());
        };
        Consumer<Person> meConsumer = restaurantConsumer.andThen(taxiConsumer);
        meConsumer.accept(me);
    }

    /**
     * 测试5：Comparators，接口中有很多默认的方法
     */
    @Test
    public void test5() {
        Person person1 = new Person("aaa", 18);
        Person person2 = new Person("bbb", 18);
        Person person3 = new Person("ccc", 30);
        Person person4 = new Person("ddd", 18);
        List<Person> personList = Arrays.asList(person1, person2, person3, person4);

        System.out.println("排序前:");
        personList.forEach((p) -> {
            System.out.print(p.getName() + "-" + p.getAge() + "\t");
        });
        //1.一般操作
        Comparator<Person> nameComparator = (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName());
        personList.sort(nameComparator);
        //2.高端操作,comparingInt,以下是不同姿势操作
        //Comparator<Person> ageComparator1 = Comparator.comparing((p) -> p.getAge());
        //Comparator<Person> ageComparator2 = Comparator.comparing(p -> p.getAge());
        //Comparator<Person> ageComparator3 = Comparator.comparingInt(p -> p.getAge());
        Comparator<Person> ageComparator4 = Comparator.comparingInt(Person::getAge);
        personList.sort(ageComparator4);
        System.out.println("\n排序后:");
        personList.forEach((p) -> {
            System.out.print(p.getName() + "-" + p.getAge() + "\t");
        });
    }

    /**
     * 测试6：Optionals不是一个函数接口,而是一个精巧的工具接口，用来防止NullPointerEception产生
     */
    @Test
    public void test6() {
        Person person = new Person("kiwipeach", 25);
        //1.Optional有值,of
        Optional<Person> optional1 = Optional.of(person);
        System.out.println(optional1.isPresent());// true

        //2.Optional为空,ofNullable
        Optional<Person> optional2 = Optional.ofNullable(null);
        System.out.println(optional2.isPresent());//false

        //3.获取内容,get
        if (optional1.isPresent()) {
            System.out.println("获取内容:" + optional1.get());
        }
        //4.如果Optional实例有值则为其调用consumer，否则不做处理，ifPresent
        optional1.ifPresent(person1 -> {
            System.out.println("ifPresent开始消费:" + person);
        });
        //5.传入对象，如果有值则将其返回，否则返回指定的其它值，orElse
        System.out.println("orElse测试:" + optional1.orElse(new Person("错误xxx")));
        System.out.println("orElse测试:" + optional2.orElse(new Person("错误xxx")));
        //6.传入Supplier接口，如果有值则将其返回，否则返回指定的其它值，orElseGet
        Person resultPerson1 = optional1.orElseGet(() -> {
            return new Person("消费者");
        });
        Person resultPerson2 = optional2.orElseGet(() -> {
            return new Person("消费者");
        });
        System.out.println("orElseGet测试:" + resultPerson1);
        System.out.println("orElseGet测试:" + resultPerson2);
        //7.如果有值则将其返回，否则返回异常，orElseThrow
        try {
            optional2.orElseThrow(() -> {
                throw new RuntimeException("person对象为空");
            });
        } catch (Throwable e) {
            System.out.println("捕获到了异常:"+e.getLocalizedMessage());
        }
    }
}
