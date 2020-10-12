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

/**
 * Create Date: 2018/03/09
 * Description: 测试类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class Java8 {

    private String version;

    /*构造方法*/
    public Java8(String version) {
        this.version = version;
    }

    /*成员方法*/
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    /*静态方法*/
    public static String sayVersion(String version) {
        return "Java8{version='" + version + '\'' + '}';
    }

}
