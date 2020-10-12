package com.tinymood.bean.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * spring 后置处理器BeanFactoryPostProcessor和BeanPostProcessor的用法和区别测试代码
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        System.out.println("这是MyBeanFactoryPostProcessor的构造器");
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //BeanFactoryPostProcessor可以修改BEAN的配置信息而BeanPostProcessor不能
        //我们在这里修改postProcessorBean的注入属性
        System.out.println("执行MyBeanFactoryPostProcessor的postProcessBeanFactory方法");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("user");

        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        if (mutablePropertyValues.contains("username")) {
            mutablePropertyValues.addPropertyValue("username", "test2");
            mutablePropertyValues.addPropertyValue("password", "password2");
        }

        System.out.println("结束MyBeanFactoryPostProcessor的postProcessBeanFactory方法");
    }
}


