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
package com.java8.demo9_repeat_anotation;

import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2018/04/10
 * Description: java8重复注解测试
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class RepeatAnotationTest {
    /*********************************java8之前-begin***********************************************************/
    @Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Authority {
        String role();
    }

    //@Authorities注解作为可以存储多个@Authority注解的容器
    @Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Authorities {
        Authority[] value();
    }


    /*********************************java8之前-end***********************************************************/


    /*********************************java8-begin***********************************************************/
    @Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Java8Authorities.class)
    @interface Java8Authority {
        String role();
    }

    @Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Java8Authorities {
        Java8Authority[] value();
    }

    /*********************************java8-begin***********************************************************/

    class BeforeJava8RepeatAnotation {

        //java8之前的重复注解使用方式
        @Authorities({@Authority(role = "Admin"), @Authority(role = "Manager")})
        public void beforeJava8() {
        }

        //java8中的重复注解，更加简洁明了
        @Java8Authority(role = "admin")
        @Java8Authority(role = "manager")
        public void inJava8() {

        }
    }

    /**
     * 测试1：两种不同重复注解比较。
     * java8之前若要使用重复注解，则必须添加注解容器;
     * 在java8中其实也是依赖容器注解,这种简化写法是一种假象,两种方法获得的效果其实是相同的。
     */
    @Test
    public void test1() {
        Method[] declaredMethods = BeforeJava8RepeatAnotation.class.getDeclaredMethods();
        //遍历类方法
        for (Method method : declaredMethods) {
            //查找method
            System.out.println("当前方法:" + method.getName());
            if (method.getName().equals("beforeJava8")) {
                //查找method上的注解
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Authorities) {
                        Authorities authorities = (Authorities) annotation;
                        Authority[] values = authorities.value();
                        //获取@Authorities注解上的值，并且循环输出注解容器中的内容
                        for (Annotation reAno : values) {
                            if (reAno instanceof Authority) {
                                Authority authority = (Authority) reAno;
                                System.out.println("角色:" + authority.role());
                            }
                        }
                    }
                }
            } else if (method.getName().equals("inJava8")) {
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                for (Annotation annotation : declaredAnnotations) {
                    if (annotation instanceof Java8Authorities) {
                        Java8Authorities java8Authorities = (Java8Authorities) annotation;
                        Java8Authority[] authorities = java8Authorities.value();
                        for (Java8Authority authority : authorities) {
                            System.out.println("角色:" + authority.role());
                        }
                    }
                }
            }
        }
    }


    /**
     * 测试2：java8中增加了注解的使用范围，java8之前只能在定义类、定义接口、定义方法、定义成员变量。
     * 而在java8之后几乎注解几乎可以使用在任何的地方。
     *
     * TYPE_USE:表示注解可以再任何用到类型的地方使用
     * TYPE_PARAMETER：表示该注解能写在类型参数的声明语句中。例如：<T>、<T extends Person>
     */
    @Target(ElementType.TYPE_USE)
    @interface NotNull {
    }

    //类
    @NotNull class JavaAnotaionDemo {
        //成员变量
        @NotNull
        private String id;

        //方法以及入参
        @NotNull
        public String getId(@NotNull String source) {
            Object word = "hello java8";
            //类型转换
            String paseWord = (@NotNull String) word;
            //创建对象
            Object object = new @NotNull Object();
            //泛型
            List<@NotNull String> stringList = new ArrayList<>();
            return source + id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Test
    public void test2() {
        //详情见此类
        JavaAnotaionDemo javaAnotaionDemo = new JavaAnotaionDemo();
    }
}
