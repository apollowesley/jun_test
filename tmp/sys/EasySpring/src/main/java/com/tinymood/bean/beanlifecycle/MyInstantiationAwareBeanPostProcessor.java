package com.tinymood.bean.beanlifecycle;


import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;


/**
 * 测试容器级生命周期接口InstantiationAwareBeanPostProcessor
 */
public class MyInstantiationAwareBeanPostProcessor extends
        InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("3、这是容器级生命周期接口InstantiationAwareBeanPostProcessorAdapter实现类构造器！");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInstantiation(Class beanClass, String beanName) throws BeansException {
        System.out.println("3、容器级生命周期接口InstantiationAwareBeanPostProcessor调用postProcessBeforeInstantiation方法");
        return null;
    }

    // 接口方法、实例化Bean之后、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.println("3、容器级生命周期接口InstantiationAwareBeanPostProcessor调用postProcessPropertyValues方法");
        return pvs;
    }

    // 接口方法、初始化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("3、容器级生命周期接口InstantiationAwareBeanPostProcessor调用postProcessAfterInitialization方法");
        return bean;
    }
}
