package com.zccoder.demo.spring.enable.plugin.support;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动插件注解
 *
 * @author zc 2018-06-28
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(PluginDemoConfiguration.class)
public @interface EnablePluginDemo {

}
