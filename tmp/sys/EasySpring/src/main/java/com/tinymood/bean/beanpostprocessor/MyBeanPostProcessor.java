package com.tinymood.bean.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * spring 后置处理器BeanFactoryPostProcessor和BeanPostProcessor的用法和区别测试代码
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        System.out.println("这是BeanPostProcessor构造器");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("====MyBeanPostProcessor的postProcessBeforeInitialization方法====");
        if (bean instanceof User) {
            System.out.println("bean处理器：userBean实例化、依赖注入完毕，init-method指定初始化方法之前");
            User user = (User)bean;
            System.out.println(user.toString());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("====MyBeanPostProcessor的postProcessAfterInitialization方法====");
        if (bean instanceof User) {
            System.out.println("bean处理器：userBean实例化、依赖注入完毕，init-method指定初始化方法之后");
            User user = (User)bean;
            System.out.println(user.toString());
        }
        return bean;
    }
}
