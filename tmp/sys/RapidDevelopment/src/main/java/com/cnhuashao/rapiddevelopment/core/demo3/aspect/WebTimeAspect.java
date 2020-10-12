package com.cnhuashao.rapiddevelopment.core.demo3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 类 {@code WebTimeAspect} web请求时间切面类 <br> 用于对所有web类请求进行访问时间记录.
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/4 19:01
 */
@Aspect
@Component
@Order(1)
public class WebTimeAspect {

    private Logger log = LoggerFactory.getLogger(WebTimeAspect.class);
    /**
     * 声明一个线程，用于记录请求与响应整个周期期间在服务端消耗的时间
     */
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * com.cnhuashao.rapiddevelopment.core包及所有子包下任何类的任何方法
     */
    @Pointcut("execution(* com.cnhuashao.rapiddevelopment.core..*.*(..))")
    public void webTime(){

    }

    /**
     * 在请求响应之前，即请求到达当前服务端
     * 所有demo3下的controller均经过该切面
     * 暂存：@Before("within(com.cnhuashao.rapiddevelopment.core..*.*)")
     * @param joinPoint
     */
    @Before("webTime()")
    public void doBefore(JoinPoint joinPoint){
        log.info("----------- WebTimeAspect doBefore -----------------------------------------");
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 在请求响应之后，即请求已经经过controller处理返回后
     * 暂存:@AfterReturning(value = "within(com.cnhuashao.rapiddevelopment.core..*.*)",returning = "rvt")
     * @param rvt
     */
    @AfterReturning(pointcut = "webTime()",returning = "rvt")
    public void doAfterReturning(Object rvt){
        log.info("-----------Start WebTimeAspect doAfterReturning ------");
        log.info("本次处理请求耗费时间 : {}",(System.currentTimeMillis() - startTime.get()));
        log.info("-----------End WebTimeAspect doAfterReturning -------------------------------");
    }

}
