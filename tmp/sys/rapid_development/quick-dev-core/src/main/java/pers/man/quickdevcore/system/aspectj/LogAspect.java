package pers.man.quickdevcore.system.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pers.man.quickdevcommon.annotation.Log;
import pers.man.quickdevcore.system.entity.log.OperateLog;

import java.lang.reflect.Method;

/**
 * 日志切面
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-02 19:47
 * @project quick-dev
 */
@Aspect
@Component
public class LogAspect {
    //切点
    @Pointcut("@annotation(pers.man.quickdevcommon.annotation.Log)")
    public void pointCut() {

    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, result, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        handleLog(joinPoint, null, exception);
    }

    private void handleLog(JoinPoint joinPoint, Object result, Exception exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        //获取到当前用户
        Object user = null;
        //将日志记录到数据库
        OperateLog operateLog = new OperateLog();
        
        return;
    }
}
