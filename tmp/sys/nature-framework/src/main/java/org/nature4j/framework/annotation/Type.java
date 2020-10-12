package org.nature4j.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nature4j.framework.enums.Types;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Type {
	Types type() default Types.STRING;

	String length() default "255";
	
	String def() default "";
}
