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
package cn.kiwipeach.loader;

/**
 * Create Date: 2018/03/21
 * Description: 测试类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class Me extends Human{
    private static Computer computer = new Computer();
    static {
        System.out.println("Me:静态代码段");
    }
    {
        System.out.println("Me:实例代码段");
    }

    public Me() {
        System.out.println("Me:默认构造函数");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        //Class.forName("cn.kiwipeach.loader.Me");
        /**
         Human:静态代码段
            Computer:静态代码段
            Computer:实例代码段
            Computer:默认构造函数
         Me:静态代码段
         */
        new Me();
        /**
         Human:静态代码段
             Computer:静态代码段
             Computer:实例代码段
             Computer:默认构造函数
         Me:静态代码段
         Human:实例代码段
         Human:默认构造函数
         Me:实例代码段
         Me:默认构造函数
         */


        /**
         * 总结：
         * 1.静态代码段和实例代码段的加载都是有先后顺序的
         * 2.类加载只是加载静态字段，若静态字段为对象，那么就先加载该对象的静态方法->实例代码段->构造函数
         * 3.若当前类存在父类，那么优先加载父类的静态代码，然后再加载父类的构造方法，最后才实例化自身。
         */
    }
}
