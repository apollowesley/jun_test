package org.jiucheng.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jiucheng.web.handler.DefaultHandler;
import org.jiucheng.web.handler.Handler;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	public Class<? extends Handler> value() default DefaultHandler.class;
}
