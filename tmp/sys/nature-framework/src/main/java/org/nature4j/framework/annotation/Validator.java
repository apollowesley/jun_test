package org.nature4j.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nature4j.framework.validator.NatureValidator;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {
	Class<? extends NatureValidator>[] value();
}
