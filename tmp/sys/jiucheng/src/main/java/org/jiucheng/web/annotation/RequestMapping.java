package org.jiucheng.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jiucheng.web.handler.DefaultOut;
import org.jiucheng.web.handler.Out;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String[] value() default {};
    RequestMethod[] method() default {};
    Class<? extends Out> out() default DefaultOut.class;
}
