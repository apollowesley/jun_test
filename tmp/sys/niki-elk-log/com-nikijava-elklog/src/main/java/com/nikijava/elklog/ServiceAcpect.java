package com.nikijava.elklog;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
  *
  * @ProjectName:
  * @Package:        com.niki.log
  * @ClassName:      ServiceAcpect
  * @Description:     类作描述
  * @Author:         Niki Zheng
  * @CreateDate:     2019/8/26 16:55
  * @UpdateRemark:   更新说明
  * @Version:        1.0
 */

@Aspect
@Component
@Slf4j
public class ServiceAcpect {

    long startTime = 0;
    long endTime = 0;

    /**
     * 定义切入点，切入点为service下的所有函数
     */
    @Pointcut("execution(* com.niki.demo..*.*(..))")
    public void requestAndResponseAcpect() {
    }

    /**
     * @param
     * @return
     * @method
     * @description 执行dubbo方法，输出类名，方法名，方法参数等重要信息
     * @date: 2019/3/5 15:52
     * @author: Niki Zheng
     */
    @Before("requestAndResponseAcpect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime = System.currentTimeMillis();
        log.info("Service请求,service方法名=={},开始时间是=={} ms", getClassMethod(joinPoint), startTime);
        log.info("Service请求参数,service方法名=={},参数类型和参数名称和参数值=={}", getClassMethod(joinPoint), getParametersLog(joinPoint));
    }

    /**
     * @param
     * @return
     * @method
     * @description 返回以后输出返回的日志信息
     * @date: 2019/3/5 15:52
     * @author: Niki Zheng
     */
    @AfterReturning(returning = "ret", pointcut = "requestAndResponseAcpect()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        log.info("Service接口响应数据，方法名=={},返回对象数据是=={}", getClassMethod(joinPoint), JsonUtils.toJson(ret));
        endTime = System.currentTimeMillis();
        log.info("Service接口响应,Service方法名=={},结束时间是=={}  ms", getClassMethod(joinPoint), endTime);
        log.info("Service接口响应,Service方法名=={},执行的时间是=={}  ms", getClassMethod(joinPoint), endTime - startTime);
    }


    /**
     * @param
     * @return
     * @method
     * @description 当系统发生异常通过切面直接打印堆栈信息，不用手动输出异常
     * @date: 2019/3/5 15:53
     * @author: Niki Zheng
     */
    @AfterThrowing(pointcut = "requestAndResponseAcpect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("doAfterThrowing系统发生异常，异常类和方法是=={}，异常信息是=={}", getClassMethod(joinPoint), ExceptionUtils.getStackTrace(e));
    }


    @Around(value = "requestAndResponseAcpect()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object baseResponse = null;
        try {
            baseResponse = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("doAround系统发生异常，异常类和方法是=={}，异常信息是=={}", getClassMethod(proceedingJoinPoint), ExceptionUtils.getStackTrace(e));
        }
        return baseResponse;
    }



    /**
     * @param
     * @return
     * @method
     * @description 方法的作用:获取请求参数对象数据
     * @date: 2019/4/8 14:49
     * @author: Niki Zheng
     */
    private String getParametersLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Class[] types = methodSignature.getParameterTypes();
        Object[] args = joinPoint.getArgs();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            stringBuilder.append("参数类型：" + types[i].getName() + "参数名称：" + parameterNames[i] + ",参数值：" + JsonUtils.toJson(args[i]) + "。");
        }
        return stringBuilder.toString();
    }

    private String getClassMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

    private String getReturnType(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getReturnType().getName();
    }

    public boolean filedExists(Field[] fields,String filedName){
        for (Field field:fields){
            if (filedName.equals(field.getName())){
                return true;
            }
        }
        return false;
    }


    /*private String getParametersLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Class[] types = methodSignature.getParameterTypes();
        Object[] args = joinPoint.getArgs();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            stringBuilder.append("参数类型：" + types[i].getName() + "参数名称：" + parameterNames[i] + ",参数值：" + args[i] + "。");
        }
        return stringBuilder.toString();
    }

    private String getClassMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

    private String getReturnType(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getReturnType().getName();
    }*/


}