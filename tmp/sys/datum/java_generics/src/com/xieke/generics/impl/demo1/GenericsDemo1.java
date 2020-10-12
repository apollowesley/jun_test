package com.xieke.generics.impl.demo1;

interface Info<T>
{ // 在接口上定义泛型
    public T getVar(); // 定义抽象方法，抽象方法的返回值就是泛型类型
}

class InfoImpl<T> implements Info<T>
{ // 定义泛型接口的子类
    private T var; // 定义属性

    public InfoImpl(T var)
    { // 通过构造方法设置属性内容
        this.setVar(var);
    }

    public void setVar(T var)
    {
        this.var = var;
    }

    @Override
    public T getVar()
    {
        return this.var;
    }
};

/**
 * 泛型接口实现的两种方式
 * 
 * 第一种：在子类的定义上也声明泛型类型
 * 
 * @author junhu
 *
 */
public class GenericsDemo1
{
    public static void main(String arsg[])
    {
        Info<String> i = null; // 声明接口对象
        i = new InfoImpl<String>("xieke"); // 通过子类实例化对象
        System.out.println("内容：" + i.getVar());
    }
};