package com.tinymood.bean.beanpostprocessor;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by hztaoran on 2016/7/1 0001.
 */
public class BeanTest {

    private ApplicationContext context;

    @Before
    public void setUp() {
        String[] paths = {"classpath*:applicationContext.xml"};

        context = new ClassPathXmlApplicationContext(paths);
    }

    @Test
    public void testBeanPostProcessor() {

    }

    @Test
    public void testBeanFactoryPostProcessor() {
        User bean = (User)context.getBean("user");
        System.out.println("======testBeanPostProcessor=======");
        System.out.println("username:" + bean.getUsername());
        System.out.println("password:" + bean.getPassword());
    }
}