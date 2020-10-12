package pers.man.quickdevcore.system.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 13:56
 * @project quick-dev
 */
@Aspect
@Component
public class ValidateAspect {

    @Pointcut("@annotation(pers.man.quickdevcommon.annotation.Validate)")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void before(){

    }
}
