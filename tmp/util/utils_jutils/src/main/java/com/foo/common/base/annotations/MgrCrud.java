package com.foo.common.base.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE })
@Retention(RUNTIME)
public @interface MgrCrud {

	/**
	 * 是否继承id2name接口
	 */
	boolean id2bean() default false;
}
