package pers.man.quickdevcore.system.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 14:04
 * @project quick-dev
 */
@Aspect
@Component
public class RepeatSubmitAspect {
    @Pointcut("@annotation(pers.man.quickdevcommon.annotation.RepeatSubmit)")
    public void pointCut() {

    }
}
