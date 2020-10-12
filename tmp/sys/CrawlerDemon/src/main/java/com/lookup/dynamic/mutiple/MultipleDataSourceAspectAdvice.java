/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.mutiple;

import com.lookup.dynamic.buss.sqlmapper.MysqlServiceMapper;
import com.lookup.dynamic.buss.sqlmapper.OracleServiceMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

    private static Logger logger = LoggerFactory.getLogger(MultipleDataSourceAspectAdvice.class);

    @Around("execution(* com.lookup.dynamic.buss.sqlmapper.*.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        if (jp.getTarget() instanceof MysqlServiceMapper) {
            MultipleDataSource.setDataSourceKey("mySqlDataSource");
        } else if (jp.getTarget() instanceof OracleServiceMapper) {
            MultipleDataSource.setDataSourceKey("oracleDataSource");
        }
        return jp.proceed();
    }
}