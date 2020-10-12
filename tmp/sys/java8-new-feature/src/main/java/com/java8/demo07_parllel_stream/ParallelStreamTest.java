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
package com.java8.demo07_parllel_stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Create Date: 2018/04/10
 * Description: 并行流操作
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class ParallelStreamTest {

    private List<String> values = new ArrayList<String>();

    /**
     * 测试1：原始排序
     */
    @Before
    public void initDate() {
        final int N = 9000000;
        for (int i = 0; i < N; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
    }

    /**
     * 测试1：顺序流操作
     */
    @Test
    public void test1() {
        //1000000 3s 570ms
        //9000000 23s 464ms
        values.stream().sorted();
    }

    /**
     * 测试2：并行流操作
     */
    @Test
    public void test2(){
        //1000000 2s 956ms
        //9000000 21s 227ms
        values.parallelStream().sorted();
    }

}
