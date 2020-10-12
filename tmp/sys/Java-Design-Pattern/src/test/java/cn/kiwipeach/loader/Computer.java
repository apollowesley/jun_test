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
 * Description: 电脑
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class Computer {
    static {
        System.out.println("Computer:静态代码段");
    }
    {
        System.out.println("Computer:实例代码段");
    }

    public Computer() {
        System.out.println("Computer:默认构造函数");
    }

}
