/*
 * Copyright 2019 liuburu@qq.com.
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


/**
 * 描述：java虚拟机参数相关验证
 *
 * @author kiwipeach
 * @create 2019-10-04
 */
public class JvmMain {
    public static void main(String[] args) {
        System.out.println(JvmMain.class);
        System.out.println("test word");
        //系统属性
       /* System.out.println("系统属性：");
        SystemUtil.dumpSystemInfo();*/
        /*Properties properties = System.getProperties();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = String.valueOf(enumeration.nextElement());
            String value = String.valueOf(properties.get(key));
            System.out.println(String.format("%s --> %s", key, value));
        }*/
        //系统环境变量
       /* System.out.println("\n\n\n环境变量：");
        Map<String, String> envs = System.getenv();
        Set<String> keySet = envs.keySet();
        for (String key : keySet) {
            String value = envs.get(key);
            System.out.println(String.format("%s --> %s", key, value));
        }*/
        //程序入参
        //System.out.println("\n\n\n程序入参：");
        for (int i = 0; i < args.length; i++) {
            System.out.println(String.format("参数%s = %s", i, args[i]));
        }
    }
}
