package com.mkfree.java_reflect;

import java.lang.annotation.*;

/**
 * @author oyhk
 * @date 2019-04-23 18:01
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HbaseColumn {

    String name();
}
