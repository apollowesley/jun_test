package com.xieke.generics.method.demo1;

class Demo
{
    public <T> T fun(T t)
    { // 可以接收任意类型的数据
        return t; // 直接把参数返回
    }
};

/**
 * 定义泛型方法
 * 
 * @author junhu
 *
 */
public class GenericsDemo1
{
    public static void main(String args[])
    {
        Demo d = new Demo(); // 实例化Demo对象
        String str = d.fun("xieke"); // 传递字符串
        int i = d.fun(22); // 传递数字，自动装箱
        System.out.println(str); // 输出内容
        System.out.println(i); // 输出内容
    }
};