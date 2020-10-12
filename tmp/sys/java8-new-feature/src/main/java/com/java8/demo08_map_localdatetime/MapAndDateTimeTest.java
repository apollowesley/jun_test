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
package com.java8.demo08_map_localdatetime;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create Date: 2018/04/10
 * Description: Map和时间日期api
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class MapAndDateTimeTest {

    /**
     * 测试1：Map常用用法
     */
    @Test
    public void test1() {
        //1.常规操作
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            //避免我们将重复的值写入
            map.putIfAbsent(i, "value" + i);
        }
        //2.改变map中的某个值
        map.computeIfPresent(1, (key, value) -> {
            return key + "****" + value;
        });
        //3.删除map中的某个值
        map.computeIfPresent(9, (key, value) -> null);
        //4.computeIfAbsent,如果原map中不存在，则添加，否则不做任何事情
        map.computeIfAbsent(23, (key) -> "kye[" + key + "] absent value");
        System.out.println("是否有key=23的值?" + map.containsKey(23));
        map.computeIfAbsent(1, key -> "已存在的key=" + key);
        //5.迭代输出map结果
        map.forEach((key, value) -> System.out.println(key + "--" + value));
    }

    /**
     * 测试2：Map的其他操作
     */
    @Test
    public void test2() {
        Map<Integer, String> map = new HashMap<>();
        map.put(101, "kakaluote");
        //1.会覆盖原来key对应的value
        //map.put(101, "kiwipeach");

        //2.不会覆盖原来key对应的value
        map.putIfAbsent(101, "kiwipeach");

        //3.合并、修改 key对应的value操作
        //map.merge(101, "author:", (oldVlue, newVlue) -> newVlue.concat(oldVlue));
        map.merge(101, "孙悟空", (oldVlue, newVlue) -> newVlue);

        //4.getOrDefault，获取一个可能不存在的值
        String mapOrDefault = map.getOrDefault(999, "不存在该值");
        System.out.println("mapOrDefault=" + mapOrDefault);
        map.forEach((key, value) -> System.out.println(key + "--" + value));
    }

    /**
     * SQL -> Java
     * --------------------------
     * date -> LocalDate
     * time -> LocalTime
     * timestamp -> LocalDateTime
     */

    /**
     * 测试3：LocalDate日期类
     */
    @Test
    public void test3() {
        //1.LocalDate简单使用
        LocalDate localDate0 = LocalDate.now();
        LocalDate localDate1 = LocalDate.of(2017, 07, 20);
        LocalDate localDate2 = LocalDate.parse("2017-07-20");
        System.out.println("localDate0: " + localDate0);
        System.out.println("localDate1: " + localDate1);
        System.out.println("localDate2: " + localDate2);
        //2.获取明天
        System.out.println("明天:" + LocalDate.now().plusDays(1));
        System.out.println("下个礼拜今天:" + LocalDate.now().plusWeeks(1));
        System.out.println("下个月今天:" + LocalDate.now().plusDays(1));
        System.out.println("明年今天:" + LocalDate.now().plusYears(1));
        //3.解析字符串日期
        System.out.println(localDate1.getYear() + "年" + localDate1.getYear() + "月" + localDate1.getDayOfMonth() + "日");
        //4.判断平年瑞年
        System.out.println("今年是不是瑞年?" + LocalDate.now().isLeapYear());
        //5.判断日期先后
        System.out.println("2018-04-09在当前时间之前吗?" + localDate0.minus(1, ChronoUnit.DAYS).isBefore(LocalDate.now()));
        //6.判断是否为同一天
        System.out.println("是否为同一天?" + localDate1.equals(localDate2));
    }


    /**
     * 测试4：LocalTime 时间类
     */
    @Test
    public void test4() {
        LocalTime nowTime = LocalTime.now();
        LocalTime parseTime = LocalTime.parse("18:57:30");
        LocalTime ofTime = LocalTime.of(19, 00, 30);
        //1.当前时间
        System.out.println("当前时间:" + nowTime);
        //2.字符串转换
        System.out.println("字符串转换:" + parseTime);
        //3.of创建时间
        System.out.println("of创建时间:" + ofTime);
        //4.获取小时、分钟、秒信息
        System.out.println("现在时间是:" + nowTime.getHour() + "时" + nowTime.getMinute() + "分" + nowTime.getSecond() + "秒");
        //5.判断时间先后
        System.out.println("时间先后判断结果?" + nowTime.isAfter(parseTime));
    }

    /**
     * 测试5：LocalDateTime 日期时间类
     */
    @Test
    public void test5() {
        //1.获取当前日期时间
        LocalDateTime dateTime = LocalDateTime.now();
        //2.格式化字符串to日期时间类
        //LocalDateTime parseDateTime = LocalDateTime.parse("2018-04-10T19:08:39.236");
        LocalDateTime parseDateTime = LocalDateTime.parse("2018-04-10 19:08:39", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //3.使用of创建日期时间
        LocalDateTime ofDateTime = LocalDateTime.of(2018, 4, 10, 19, 10, 30);
        System.out.println("当前时间是:" + dateTime);
        System.out.println("字符串格式化:" + parseDateTime);
        System.out.println("of创建日期时间:" + ofDateTime);
        //4.日期转字符串，字符串转日期
        System.out.println("日期转字符串:" + dateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
        System.out.println("字符串转日期:" + LocalDateTime.parse("2018年04月10日 19:24:31", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
    }

    /**
     * 测试6：与Date之前的相互转换
     */
    @Test
    public void test6() {
        //1.Date与LocalDate转换
        Date localDate2Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate date2LocalDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("localDate2Date=" + localDate2Date);
        System.out.println("date2LocalDate=" + date2LocalDate);

        //2.Date与LocleDateTime转换
        Date localDateTime2Date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime Date2LocalDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        System.out.println("localDateTime2Date=" + localDateTime2Date);
        System.out.println("Date2LocalDateTime=" + Date2LocalDateTime);

        //3.Date 与LocalTime转换,通过localDateTime来进行转换
        LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        Date localTime2Date = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        LocalTime date2LocalTime = localDateTime2.toLocalTime();
    }


}
