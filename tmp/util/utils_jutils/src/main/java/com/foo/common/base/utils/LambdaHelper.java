package com.foo.common.base.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public enum LambdaHelper {

    instance;

    private static Logger logger = LoggerFactory.getLogger(LambdaHelper.class);

    public static void main(String a[]) {
        Runnable runnable = () -> {
            System.out.println("okay.");
        };
        logger.info("run now.");
        runnable.run();

        instance.simplePrint(Lists.newArrayList("dasdad", "2", 189), (x) -> {
            Integer.parseInt((String) x);
            return;
        });

    }

    private <T> void simplePrint(List<T> myList, Consumer<T> c) {
        for (T t : myList) {
            c.accept(t);
        }
    }


}
