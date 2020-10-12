package pers.man.quickdevcommon.annotation;

import java.lang.annotation.*;

/**
 * 用于数据校验的注解
 * @author MAN
 * @version 1.0
 * @date 2020-04-02 19:05
 * @project quick-dev
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
}
