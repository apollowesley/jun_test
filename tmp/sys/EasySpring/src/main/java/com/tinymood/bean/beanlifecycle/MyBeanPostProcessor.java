package com.tinymood.bean.beanlifecycle;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 测试容器级生命周期接口BeanPostProcessor
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("3、这是容器级生命周期接口BeanPostProcessor实现类构造器！");
    }

    @Override
    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("3、容器级生命周期接口方法-BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！");
        return arg0;
    }

    @Override
    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("3、容器级生命周期接口方法-BeanPostProcessor接口方法postProcessBeforeInitialization对属性进行更改！");
        return arg0;
    }
}
