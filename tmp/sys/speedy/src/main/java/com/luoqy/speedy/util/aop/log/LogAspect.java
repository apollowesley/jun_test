package com.luoqy.speedy.util.aop.log;

import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.IpAddressUtils;
import net.sf.json.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *  切面处理方法
 *      日志操控记录
 * */
@Aspect
@Component
public class LogAspect {
    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.luoqy.speedy.util.aop.log.Log)")
    public void logPoinCut() {
    }
    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        Log myLog = method.getAnnotation(Log.class);
        if (myLog != null) {
            //注册值
            String[] values = myLog.value();//保存获取的操作
            //操作描述
            String handleDesc=values[0];
            //其他描述
            String handleOther=values[1];
        }
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        System.err.println(className);
        //获取请求的方法名
        String methodName = method.getName();
        System.err.println(methodName);
        /***/
        //请求的参数值
        Object[] args = joinPoint.getArgs();//[]
        String[] names=signature.getParameterNames();//[]
        System.err.println(Arrays.toString(names));//[name,id]
        //将参数所在的数组转换成json
        if(args.length>0){
            for(int i=0;i<args.length;i++){
                System.err.println(signature.getParameterNames()[i]+":"+args[i]);
            }
        }

       // String params = Arrays.toString(args);/[1,2]
        //System.err.println(params);
        /***/
        //获取用户名
        //获取用户ip地址
        String ip=IpAddressUtils.INTERNET_IP;
        System.err.println(ip);
        //调用service保存SysLog实体类到数据库
        //MySqldbUtil.mysqlExecute("insert into ");
    }
}
