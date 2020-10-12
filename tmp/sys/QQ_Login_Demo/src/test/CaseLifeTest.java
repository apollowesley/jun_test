/*
 * Copyright 2018 kiwipeach.
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * junit单元测试生命周期测试
 *
 * @author kiwipeach [1099501218@qq.com]
 * @create 2018/09/24
 */
public class CaseLifeTest {
    @Before
    public void init(){
        System.out.println("before");
    }
    @After
    public void destrory(){
        System.out.println("destrory");
    }

    @Test
    public void method1(){
        System.out.println("test1");
    }

    @Test
    public void method2(){
        System.out.println("test2");
    }
}
