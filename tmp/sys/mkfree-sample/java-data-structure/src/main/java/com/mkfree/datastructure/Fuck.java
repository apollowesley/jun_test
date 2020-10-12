package com.mkfree.datastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Fuck {

    public static void main(String[] args) throws IOException {
        Stream<String> stream = Files.lines(Paths.get("/Users/oyhk/Downloads/1279484801_Trade_2018-05-12_2018-06-11.csv"), StandardCharsets.UTF_8);

        Map<String, Map<String, String>> data = new HashMap<>();
        stream.forEach(s -> {
            String[] a = s.split(",");
            Map<String, String> temp = new HashMap<>();
            temp.put("created_at", a[0].replaceAll("\"", "").trim());
            temp.put("external_order_num", a[1].replaceAll("\"", "").trim());
            temp.put("price", a[11].replaceAll("\"", "").trim());
            data.put(temp.get("external_order_num"), temp);
        });

        Stream<String> stream1 = Files.lines(Paths.get("/Users/oyhk/Downloads/1279484801_Trade_2018-06-10_2018-07-09.csv"), StandardCharsets.UTF_8);
        stream1.forEach(s -> {
            String[] a = s.split(",");
            Map<String, String> temp = new HashMap<>();
            temp.put("created_at", a[0].replaceAll("\"", "").trim());
            temp.put("external_order_num", a[1].replaceAll("\"", "").trim());
            temp.put("price", a[11].replaceAll("\"", "").trim());
            data.put(temp.get("external_order_num"), temp);
        });



        Set<String> databaseOrderNum = new HashSet<>();
        // 读取数据库所有对账
        Stream<String> databaseYicui = Files.lines(Paths.get("/Users/oyhk/Downloads/wechat_yicui_antbox_prod.csv"), StandardCharsets.UTF_8);
        databaseYicui.forEach(s -> {
            String[] a = s.split(",");
            databaseOrderNum.add(a[0].replaceAll("\"", "").trim());
        });

        Stream<String> databaseYiwo = Files.lines(Paths.get("/Users/oyhk/Downloads/wechat_yicui_canteen_prod.csv"), StandardCharsets.UTF_8);
        databaseYiwo.forEach(s -> {
            String[] a = s.split(",");
            databaseOrderNum.add(a[0].replaceAll("\"", "").trim());
        });

        Map<String, Map<String, String>> data1 = new HashMap<>();
        databaseOrderNum.forEach(external_order_num -> {

            if(data.containsKey(external_order_num)){
                data1.put(external_order_num,data.get(external_order_num));
            }
        });

        double[] totalPrice = new double[1];
        data1.forEach((s, stringStringMap) -> {
            totalPrice[0]  = totalPrice[0]+Double.valueOf(stringStringMap.get("price"));
        });
        System.out.println(data1.size());
        System.out.println(totalPrice[0]);
    }
}
