package com.flypigs.resubmit;

import com.flypigs.MethodUtils;
import com.flypigs.lock.DistributedLock;
import com.flypigs.lock.LockManager;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 防重刷aop
 * 后期：把aop与分布式锁拆分
 *
 * @author uncle yang
 * @date 2018-05-15
 */
@Aspect
public class ResubmitAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResubmitAspect.class);
    private final static Integer DEFAULT_SECONDS = 5;//默认过期时间

    @Autowired
    ApplicationContext applicationContext;
    @Autowired(required = false)
    LockManager lockManager;

    @Autowired
    ResubmitResolvers resubmitResolvers;

    @Pointcut("@annotation(resubmit)")
    public void method(Resubmit resubmit) {
    }

    @Around("method(resubmit)")
    public Object arround(ProceedingJoinPoint pjp, Resubmit resubmit) throws Throwable {
        DistributedLock distributedLock = null;
        Method method = null;
        ResubmitResolver resubmitResolver = null;
        if (lockManager != null) {
            try {
                //获取参数名及参数值
                Signature signature = pjp.getSignature();
                method = ((MethodSignature) signature).getMethod();
                //取resolver
                resubmitResolver = resubmitResolvers.getMatchedResubmitResolver(method, resubmit);
                //取spEL表达式
                String prefix = resubmit.lockPrefix();
                prefix = prefix == null ? "" : prefix;
                Integer expireSeconds = resubmit.expireSeconds();
                expireSeconds = expireSeconds == null || expireSeconds <= 0 ? DEFAULT_SECONDS : expireSeconds;
                String spEL = resubmit.lockKey();
                if (StringUtils.isNotBlank(spEL)) {
                    //获取参数名和值
                    Map<String, Object> nameAndValues = MethodUtils.parse(method, pjp.getArgs());

                    //执行el获取值
                    Object value = MethodUtils.parseSpElValue(spEL, nameAndValues);

                    //加锁
                    if (value != null) {
                        final String key = prefix + value.toString();
                        distributedLock = lockManager.getLock();
                        if (distributedLock != null) {
                            distributedLock.tryLock(key, expireSeconds);
                        }
                    }

                }
            } catch (Exception e) {
                //无论成败，不允许影响正常业务
                e.printStackTrace();
            }


            //拦截重复提交后：回调；
            if (distributedLock != null && distributedLock.isLocked()) {
                LOGGER.warn("不能重复提交");
                if (resubmitResolver != null) {
                    Object result = resubmitResolver.resolve(method);
                    if (resubmitResolver.isReturn()) {
                        return result;
                    }
                }
            }

        } else {
            System.out.println("lockManager不存在");
            LOGGER.warn("lockManager不存在");
        }

        //正常业务
        try {
            Object o = pjp.proceed();
            return o;
        } finally {
            if (distributedLock != null) {
                distributedLock.unlock();
            }
        }
    }
}
