package com.xieke.generics.method.demo2;

class Info<T extends Number>
{ // 指定上限，只能是数字类型
    private T var; // 此类型由外部决定

    public T getVar()
    {
        return this.var;
    }

    public void setVar(T var)
    {
        this.var = var;
    }

    @Override
    public String toString()
    { // 覆写Object类中的toString()方法
        return this.var.toString();
    }
};

/**
 * 通过泛型方法返回泛型类的实例
 * 
 * @author junhu
 *
 */
public class GenericsDemo2
{
    public static void main(String args[])
    {
        Info<Integer> i = fun(22);
        System.out.println(i.getVar());
    }

    public static <T extends Number> Info<T> fun(T param)
    {
        Info<T> temp = new Info<T>(); // 根据传入的数据类型实例化Info
        temp.setVar(param); // 将传递的内容设置到Info对象的var属性之中
        return temp; // 返回实例化对象
    }
};