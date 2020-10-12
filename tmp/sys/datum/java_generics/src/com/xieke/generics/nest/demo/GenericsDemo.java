package com.xieke.generics.nest.demo;

class Info<T, V>
{ // 接收两个泛型类型
    private T var;
    private V value;

    public Info(T var, V value)
    {
        this.setVar(var);
        this.setValue(value);
    }

    public void setVar(T var)
    {
        this.var = var;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    public T getVar()
    {
        return this.var;
    }

    public V getValue()
    {
        return this.value;
    }
};

class Demo<S>
{
    private S info;

    public Demo(S info)
    {
        this.setInfo(info);
    }

    public void setInfo(S info)
    {
        this.info = info;
    }

    public S getInfo()
    {
        return this.info;
    }
};

public class GenericsDemo
{
    public static void main(String args[])
    {
        Demo<Info<String, Integer>> d = null; // 将Info作为Demo的泛型类型
        Info<String, Integer> i = null; // Info指定两个泛型类型
        i = new Info<String, Integer>("xieke", 22); // 实例化Info对象
        d = new Demo<Info<String, Integer>>(i); // 在Demo类中设置Info类的对象
        System.out.println("内容一：" + d.getInfo().getVar());
        System.out.println("内容二：" + d.getInfo().getValue());
    }
};
