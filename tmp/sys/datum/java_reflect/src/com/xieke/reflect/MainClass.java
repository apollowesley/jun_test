package com.xieke.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 测试Class类
 * 
 * @author junhu
 */
@SuppressWarnings("unused")
public class MainClass
{
    public static void main(String[] args)
    {
        // testOne();

        // testTwo();

        // testThree();

        // testFour();

        // testFive();
    }

    /**
     * 通过Class获得其他类的所有属性和方法
     */
    private static void testFive()
    {
        Class<?> demo = null;
        try
        {
            demo = Class.forName("com.xieke.reflect.Person");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("===============本类属性========================");
        // 取得本类的全部属性
        Field[] field = demo.getDeclaredFields();
        for (int i = 0; i < field.length; i++)
        {
            // 权限修饰符
            int mo = field[i].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = field[i].getType();
            System.out.println(priv + " " + type.getName() + " " + field[i].getName() + ";");
        }

        System.out.println("===============本类方法========================");
        Method method[] = demo.getMethods();
        for (int i = 0; i < method.length; ++i)
        {
            Class<?> returnType = method[i].getReturnType();
            Class<?> para[] = method[i].getParameterTypes();
            int temp = method[i].getModifiers();
            System.out.print(Modifier.toString(temp) + " ");
            System.out.print(returnType.getName() + "  ");
            System.out.print(method[i].getName() + " ");
            System.out.print("(");
            for (int j = 0; j < para.length; ++j)
            {
                System.out.print(para[j].getName() + " " + "arg" + j);
                if (j < para.length - 1)
                {
                    System.out.print(",");
                }
            }
            Class<?> exce[] = method[i].getExceptionTypes();
            if (exce.length > 0)
            {
                System.out.print(") throws ");
                for (int k = 0; k < exce.length; ++k)
                {
                    System.out.print(exce[k].getName() + " ");
                    if (k < exce.length - 1)
                    {
                        System.out.print(",");
                    }
                }
            }
            else
            {
                System.out.print(")");
            }
            System.out.println();
        }
    }

    /**
     * 通过Class调用其他类中的构造函数
     */
    private static void testFour()
    {
        Class<?> demo = null;
        try
        {
            demo = Class.forName("com.xieke.reflect.Person");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Person per1 = null;
        Person per2 = null;
        Person per3 = null;
        Person per4 = null;
        // 取得全部的构造函数
        Constructor<?> cons[] = demo.getConstructors();
        try
        {
            per1 = (Person) cons[0].newInstance();
            per2 = (Person) cons[1].newInstance("xieke");
            per3 = (Person) cons[2].newInstance(20);
            per4 = (Person) cons[3].newInstance("xieke", 20);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(per1);
        System.out.println(per2);
        System.out.println(per3);
        System.out.println(per4);
    }

    /**
     * 通过Class调用其他类的无参构造实例化对象
     */
    private static void testThree()
    {
        Class<?> demo = null;
        try
        {
            demo = Class.forName("com.xieke.reflect.Person");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Person per = null;
        try
        {
            per = (Person) demo.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        per.setName("xieke");
        per.setAge(22);
        System.out.println(per);
    }

    /**
     * 通过一个对象获得完整的包名和类名
     */
    private static void testOne()
    {
        Person person = new Person();
        System.out.println(person.getClass().getName());// 获得完整的包名和类名
        System.out.println(person.getClass().getSimpleName());// 获得类名
    }

    /**
     * 实例化Class类对象
     */
    private static void testTwo()
    {
        Class<?> class1 = null;
        Class<?> class2 = null;
        Class<?> class3 = null;
        try
        {
            // 一般尽量采用这种形式
            class1 = Class.forName("com.xieke.reflect.Person");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        class2 = new Person().getClass();
        class3 = Person.class;

        System.out.println("类名称   " + class1.getName());
        System.out.println("类名称   " + class2.getName());
        System.out.println("类名称   " + class3.getName());
    }
}
