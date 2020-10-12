/*
 * Copyright 2017 KiWiPeach.
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
package cn.kiwipeach.demo;

import cn.kiwipeach.demo.anotation.Kiwipeach;

/**
 * Create Date: 2018/01/14
 * Description: 作者类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@Kiwipeach("It is Author Class.")
public class Author {

    @Kiwipeach("It is id Field")
    private String id;

    @Kiwipeach("It is name Field")
    private String name;

    @Kiwipeach("It is email Field")
    private String email;

    @Kiwipeach("It is method")
    public void showDescription(
            @Kiwipeach("参数1") String param1,
            @Kiwipeach("参数2")String param2,
            @Kiwipeach("参数3")String param3
            ){
        System.out.println("Author showDescription method invoked...");
    }

}
