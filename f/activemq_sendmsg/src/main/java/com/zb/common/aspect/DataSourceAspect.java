package com.zb.common.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zb.common.annotation.DataSource;
import com.zb.common.annotation.dynamicDataSource.DynamicDataSourceHolder;

/**
 * AOP切面：数据源的注入 参考 http://www.cnblogs.com/surge/p/3582248.html
 *
 */
@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("execution(* com.zb.service.*.*.*(..))")
    private void datasource() {
        System.out.println("动态数据源切换拦截...");
    };

    /**
     * ProceedingJoinPoint：由于是方法级别的拦截，所以使用ProceedingJoinPoint，而不是JoinPoint。
     * 因为ProceedingJoinPoint有proceed()方法(执行方法体，并返回结果)。
     * 由于是拦截的方法，所以方法也许有返回值，所以使用ProceedingJoinPoint对象，同时该方法返回Object。
     * 
     * @param point
     * @return
     */
    @Around("datasource()")
    public Object doMethod(ProceedingJoinPoint point) {
        //System.out.println("进入了DataSourceAspect的doMethod方法....");

        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?>[] classz = target.getClass().getInterfaces();
        
        System.out.println("拦截的方法：" + target.getClass().getName() + "\t" + method);
        
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            // 有@DataSource注解的方法
            if (m != null && m.isAnnotationPresent(DataSource.class)) {

                // 获取该注解对象。
                DataSource data = m.getAnnotation(DataSource.class);

                // 注入对应的数据源
                String type = data.value().getType();
                DynamicDataSourceHolder.putDataSource(type);

                //LoggerUtil.LoggerFormatPattern("方法{0}请求的数据源是:{1}", method, type);
            }

        } catch (Exception e) {
        }

        //具体的业务方法要在最后执行，前面需要进行注入数据源
        Object obj = null;
        try {
            obj = point.proceed();// 这是核心步骤。作用是让方法继续往下执行，并返回执行的结果
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}