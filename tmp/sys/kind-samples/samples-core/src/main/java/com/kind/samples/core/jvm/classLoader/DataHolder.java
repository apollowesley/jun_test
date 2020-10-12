package com.kind.samples.core.jvm.classLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试数据封装类
 */
public class DataHolder {
    private static AtomicLong count = new AtomicLong();
    public static List<String> list = new ArrayList<String>();

    {
        count.incrementAndGet();
        System.out.println("current count value is " + count);
    }
}
