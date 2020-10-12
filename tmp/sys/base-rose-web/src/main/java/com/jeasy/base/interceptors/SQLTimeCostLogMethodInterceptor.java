package com.jeasy.base.interceptors;

import net.paoding.rose.jade.annotation.SQL;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 重写了TimeCostLogMethodInterceptor的一个方法
 * 以附加SQL信息在其中
 * @author taomk
 * @version 1.0
 * @since 2014/9/17 10:26
 */
@Slf4j
public class SQLTimeCostLogMethodInterceptor extends TimeCostLogMethodInterceptor {

    protected String generateKey(Method method) {
        SQL sqlAnnotation = method.getAnnotation(SQL.class);
        if (sqlAnnotation != null) {
            return super.generateKey(method) + " | " + StringUtils.replaceChars(sqlAnnotation.value(), '\n', ' ');
        } else {
            return super.generateKey(method);
        }
    }
}
