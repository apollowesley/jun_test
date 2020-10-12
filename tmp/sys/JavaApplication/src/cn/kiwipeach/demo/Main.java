package cn.kiwipeach.demo;

import cn.hutool.system.RuntimeInfo;

public class Main {

    public static void main(String[] args) {
        System.out.println(Main.class);
        System.out.println("Hello World!!!");
        System.out.println("入参信息;");
        for (int i = 0; i < args.length; i++) {
            System.out.println(String.format("Main参数%s = %s", i, args[i]));
        }
        System.out.println(new RuntimeInfo().toString());
    }
}
