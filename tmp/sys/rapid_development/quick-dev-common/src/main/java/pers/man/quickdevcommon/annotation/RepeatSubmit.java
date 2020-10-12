package pers.man.quickdevcommon.annotation;

import java.lang.annotation.*;

/**
 * 校验表单重复提交的注解
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 14:01
 * @project quick-dev
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
}
