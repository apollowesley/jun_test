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
package com.java8.demo06_stream_api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Create Date: 2018/04/10
 * Description: java8的Stream测试
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class StreamApiTest {

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

    private List<Person> personList = new ArrayList<Person>();

    @Before
    public void initData() {
        Person person1 = new Person("aaa", 18);
        Person person2 = new Person("bbb", 20);
        Person person3 = new Person("ccc", 30);
        Person person4 = new Person("ddd", 18);
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);
    }

    /**
     * 测试1：Filter接受一个predicate接口类型的变量，并将所有流对象中的元素进行过滤
     */
    @Test
    public void test1() {
        Predicate<Person> agePredicate = person -> person.getAge() > 18;
        //personList.stream().filter(agePredicate).forEach(person -> System.out.println(person));
        System.out.println("大于18岁的成年人:");
        personList
                .stream()
                .filter(agePredicate)
                .forEach(System.out::println);
    }

    /**
     * 测试2：Sorted是一个中间操作，能够返回一个排过序的流对象的视图。流对象中的元素会默认按照自然顺序进行排序
     */
    @Test
    public void test2() {
        Comparator<Person> nameComparator = (p1, p2) -> {
            return p1.getName().compareToIgnoreCase(p2.getName());
        };
        Comparator<Person> ageComparator = Comparator.comparing(Person::getAge);
        //与下面注释部分等价
        System.out.println("排序前:");
        personList.forEach(System.out::println);
        System.out.println("排序后:");
        //personList.sort(ageComparator);
        //personList.sort(nameComparator);
        personList.stream()
                .sorted(nameComparator)
                .sorted(ageComparator)
                .forEach(System.out::println);
/*
        List<Person> toSort = new ArrayList<>();
        for (Person person : personList) {
            toSort.add(person);
        }
        toSort.sort(personComparator);
        for (Person person : toSort) {
            System.out.println(person);
        }
*/
    }

    /**
     * 测试3：Map能够把流对象中的每一个元素对应到另外一个对象上
     */
    @Test
    public void test3() {
        personList.stream()
                .map(person -> {
                    person.setName(person.getName().toUpperCase());
                    return person;
                })
                .forEach(System.out::println);
    }

    /**
     * 测试4：Match判断集合元素是否匹配predicate条件
     */
    @Test
    public void test4() {
        Predicate<Person> agePredicate = person -> person.getAge() > 25;
        System.out.println("全部都大于25岁?" + personList.stream().allMatch(agePredicate));
        System.out.println("存在有人大于25岁?" + personList.stream().anyMatch(agePredicate));
        System.out.println("没有一个人大于25岁?" + personList.stream().noneMatch(agePredicate));
    }

    /**
     * 测试5：Count返回包含的元素数量。
     */
    @Test
    public void test5() {
        Predicate<Person> agePredicate = person -> person.getAge() > 17;
        long count = personList.stream().filter(agePredicate).count();
        System.out.println(count);
    }

    /**
     * 测试6：对元素进行削减操作，该操作的结果会放在一个Optional变量里返回
     */
    @Test
    public void test6() {
        //返回年龄最大的人
        Optional<Person> reduceResult = personList.stream().reduce((p1, p2) -> {
            return p1.getAge()>p2.getAge()?p1:p2;
        });
        reduceResult.ifPresent(System.out::println);
    }
}
