package com.jeasy.base.interceptors;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;

/**
 * 对拦截的方法进行计数和计时统计，
 * 通过java.util.concurrent包减少对拦截方法本身的性能影响。
 * @author taomk
 * @version 1.0
 * @since 2014/9/17 10:26
 */
@Slf4j
public class TimeCostLogMethodInterceptor implements MethodInterceptor {

    private volatile ConcurrentHashMap<String, AtomicLong> timeMap = new ConcurrentHashMap<>();

    private volatile ConcurrentHashMap<String, AtomicLong> countMap = new ConcurrentHashMap<>();

    private final Map<Method, String> keys = new ConcurrentHashMap<>();

    public Map<String, AtomicLong> newTimeMap() {
        Map<String, AtomicLong> oldMap = timeMap;
        timeMap = new ConcurrentHashMap<>();
        return oldMap;
    }

    public Map<String, AtomicLong> newCountMap() {
        Map<String, AtomicLong> oldMap = countMap;
        countMap = new ConcurrentHashMap<>();
        return oldMap;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long timeCost = endTime - startTime;
            String key = generateKeyWithCache(invocation.getMethod());
            incTime(key, timeCost);
            incCount(key);
        }
    }

    protected String generateKeyWithCache(Method method) {
        String cachedObject = keys.get(method);
        if (cachedObject == null) {
            cachedObject = generateKey(method);
            keys.put(method, cachedObject);
        }
        return cachedObject;
    }

    protected String generateKey(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        return clazz.getName() + "#" + method.getName();
    }

    private void incTime(String key, long time) {
        AtomicLong oldValue;
        if (!timeMap.containsKey(key)) {
            oldValue = timeMap.putIfAbsent(key, new AtomicLong(time));
        }
        else {
            oldValue = timeMap.get(key);
        }
        if (oldValue != null) {
            oldValue.addAndGet(time);
        }
    }

    private void incCount(String key) {
        AtomicLong oldValue;
        if (!countMap.containsKey(key)) {
            oldValue = countMap.putIfAbsent(key, new AtomicLong(1));
        }
        else {
            oldValue = countMap.get(key);
        }
        if (oldValue != null) {
            oldValue.addAndGet(1);
        }
    }
}
