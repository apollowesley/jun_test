package com.managementsystem.notification;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class SleepHelper implements MethodBeforeAdvice,AfterReturningAdvice{

	@Override
    public void before(Method arg0, Object[] arg1, Object arg2)
            throws Throwable {
        System.out.println("睡觉前要脱衣服！");
    }

    @Override
    public void afterReturning(Object arg0, Method arg1, Object[] arg2,
            Object arg3) throws Throwable {
        System.out.println("起床后要穿衣服！");
    }
}
