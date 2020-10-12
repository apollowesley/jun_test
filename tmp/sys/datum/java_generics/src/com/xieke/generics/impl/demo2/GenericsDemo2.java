package com.xieke.generics.impl.demo2;

interface Info<T>
{ // 在接口上定义泛型
    public T getVar(); // 定义抽象方法，抽象方法的返回值就是泛型类型
}

class InfoImpl implements Info<String>
{ // 定义泛型接口的子类
    private String var; // 定义属性

    public InfoImpl(String var)
    { // 通过构造方法设置属性内容
        this.setVar(var);
    }

    public void setVar(String var)
    {
        this.var = var;
    }

    @Override
    public String getVar()
    {
        return this.var;
    }
};

/**
 * 泛型接口实现的两种方式
 * 
 * 第二种：子类不使用泛型声明，在实现接口的时候直接指定好其具体的操作类型
 * 
 * @author junhu
 *
 */
public class GenericsDemo2
{
    public static void main(String arsg[])
    {
        Info i = null; // 声明接口对象
        i = new InfoImpl("xieke"); // 通过子类实例化对象
        System.out.println("内容：" + i.getVar());
    }
};