/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.desin.test;

import cn.kiwipeach.bean.UserBean;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;

/**
 * Create Date: 2017/12/06
 * Description: 设计模式运用
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class JavaDesginTest {

    /**
     * 测试1：利用反射获取JavaBean的属性
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void testGetAllProperties() throws ClassNotFoundException {
        Class cla = Class.forName("cn.kiwipeach.bean.UserBean");
        getDeclareAttributes(cla);
        getDeclareConstructors(cla);
        getDeclareMethods(cla);
    }

    /**
     * 测试2：利用反射修改Bean值，可以不提供get、set方法都能访问javabean属性
     */
    @Test
    public void testSetBeanField() throws NoSuchFieldException, IllegalAccessException {
        UserBean userBean = new UserBean("10086", "Tom");
//        userBean.setAge(25);
        userBean.email = "abc@email.com";
        System.out.println(userBean);
        Class cla = userBean.getClass();

        //设置private id
        Field idField = cla.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(userBean, "4444");

        //设置default name
        Field nameField = cla.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(userBean, "卡卡罗特");

        System.out.println(userBean);
    }

    /**
     * 测试3：测试java内省机制,内省方式需要为bean提供get和set方法
     */
    @Test
    public void testNeiXingFlect() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        //1.第一种方式PropertyDescriptor
        UserBean userBean = new UserBean("10086", "Tom");
        //内省方式读取id的值；
        PropertyDescriptor pd = new PropertyDescriptor("id", userBean.getClass());
        Method getIdMethod = pd.getReadMethod();
        Object retVal = getIdMethod.invoke(userBean);
        System.out.println(retVal);
        //内省方式改写id的值,接触getter/setter；
        PropertyDescriptor pd2 = new PropertyDescriptor("name", userBean.getClass());
        Method setIdMethod = pd2.getWriteMethod();
        setIdMethod.invoke(userBean, "4444");
        System.out.println(userBean);

        //2.第二种方式BeanInfo
        //BeanInfo对象包含了javabean对象的所有属性信息；
        BeanInfo beanInfo = Introspector.getBeanInfo(userBean.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd3 : pds) {
            if (pd3.getName().equals("name")) {//判断得到的属性是不是name；
                pd3.getWriteMethod().invoke(userBean, "KiWiPeach");
                break;
            }
        }
        System.out.println(userBean);

    }


    public static void getDeclareAttributes(Class cla) {
        System.out.println("--------获取" + cla.getName()
                + "的所有的《成员属性》相关信息-----------");
        Field fields[] = cla.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            int modal = fields[i].getModifiers();
            System.out.println(Modifier.toString(modal) + " "
                    + fields[i].getName());
        }
    }

    /**
     * 无法访问到私有构造函数
     *
     * @param cla
     */
    public static void getDeclareConstructors(Class cla) {
        System.out.println("--------获取" + cla.getName()
                + "的所有《构造方法》相关属性-----------");
        Constructor constructors[] = cla.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            int modal = constructors[i].getModifiers();
            System.out.print(Modifier.toString(modal) + " "
                    + subPackage(constructors[i].getName()) + "（");
            Parameter[] params = constructors[i].getParameters();
            int n = 0;
            for (Parameter param : params) {
                n++;
                int mo = param.getModifiers();
                String point = (n == params.length) ? "" : ",";
                System.out.print(subPackage(param.getType().getName()) + " "
                        + param.getName() + point);
            }
            System.out.println(")");
        }
    }

    /**
     * 获取所有的对象方法
     *
     * @param cla
     */
    public static void getDeclareMethods(Class cla) {
        System.out.println("--------获取" + cla.getName()
                + "的所有《方法》相关属性-----------");
        Method methods[] = cla.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            int modal = methods[i].getModifiers();
            System.out.print(Modifier.toString(modal) + " "
                    + subPackage(methods[i].getReturnType().getName()) + " "
                    + methods[i].getName() + " (");
            Parameter[] params = methods[i].getParameters();
            int n = 0;
            for (Parameter param : params) {
                n++;
                int mo = param.getModifiers();
                String point = (n == params.length) ? "" : ",";
                System.out.print(subPackage(param.getType().getName()) + " "
                        + param.getName() + point);
            }
            System.out.println(")");
        }
    }

    /**
     * 切割字符串 jxufe.liuburu.vo.User--->User
     *
     * @param srcString
     * @return
     */
    public static String subPackage(String srcString) {
        int beginIndex = srcString.lastIndexOf(".");
        return srcString.substring(beginIndex + 1);
    }
}
