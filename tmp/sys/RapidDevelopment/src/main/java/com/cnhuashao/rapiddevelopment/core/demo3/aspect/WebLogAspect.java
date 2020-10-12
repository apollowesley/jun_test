package com.cnhuashao.rapiddevelopment.core.demo3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 类 {@code WebLogAspect} 所有web请求响应访问记录切面 <br> 用于记录所有web请求响应的访问记录与结束记录.
 *
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/4 19:20
 */
@Aspect
@Component
@Order(2)
public class WebLogAspect {

    private Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 访问localhost时打印的IP地址
     */
    private static final String IP_LOCALHOST ="0:0:0:0:0:0:0:1";

    /**
     * com.cnhuashao.rapiddevelopment.core包及所有子包下任何类的任何方法
     */
    @Pointcut("execution(* com.cnhuashao.rapiddevelopment.core..*.*(..))")
    public void webLog(){

    }

    /**
     * 在请求响应之前，即请求到达当前服务端
     * 所有demo3下的controller均经过该切面
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        log.info("  ---------- WebLogAspect doBefore --------------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //在系统启动时拦截器经过该位置时会触发空指针异常，这里需要进行非空判断
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            log.info("  地址: {}", request.getRequestURL().toString());
            log.info("  请求方式: ", request.getMethod());
            String ip = request.getRemoteAddr();
            if (!IP_LOCALHOST.equals(ip)){
                log.info("  客户端IP: {}", ip);
            }
            log.info("  请求参数: {}" + Arrays.toString(joinPoint.getArgs()));
        }
    }

    /**
     * 在请求响应之后，即请求已经经过controller处理返回后
     * @param rvt
     */
    @AfterReturning(pointcut = "webLog()",returning = "rvt")
    public void doAfterReturning(Object rvt){
        log.info("  ------------Start WebLogAspect doAfterReturning ------");
        log.info("  响应结果 : {}",rvt.toString());
        log.info("  ------------End WebLogAspect doAfterReturning ------");
        log.info("");
    }

    /**
     * 异常切入
     * @param error
     */
    @AfterThrowing(pointcut = "webLog()",throwing = "error")
    public void doAfterThrowing(Throwable error){
        log.error("  ------------Start WebLogAspect doAfterThrowing ------");
        log.error("请求处理过程中发生异常：{}",error.getMessage());
        log.error("  ------------Start WebLogAspect doAfterThrowing ------\t");
        log.info("");
    }
}
