package pers.man.quickdevcommon.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:47
 * @project quick-dev
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 操作模块
     * @return
     */
    String value() default "";
}
