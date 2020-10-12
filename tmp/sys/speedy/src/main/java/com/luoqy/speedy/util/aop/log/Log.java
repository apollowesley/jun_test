package com.luoqy.speedy.util.aop.log;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;
/**
 *  日志切面编程
 * */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
@Mapping
public @interface Log {
    String[] value() default {"",""};
}
