package cn.kiwipeach.demo;

import cn.kiwipeach.demo.anotation.Kiwipeach;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2018/01/14
 **/
public class AuthorTest {

    @Test
    public void testAnotation() throws ClassNotFoundException {
        Class cla = Class.forName("cn.kiwipeach.demo.Author");
        Annotation annotation = cla.getAnnotation(Kiwipeach.class);
        //获取类上面的注解值
        System.out.println("1.获取类注解信息");
        if (annotation instanceof Kiwipeach) {
            Kiwipeach kiwipeach = (Kiwipeach) annotation;
            System.out.println("类上面注解的值为:" + kiwipeach.value());
        }
        //获取类成员属性的注解值
        System.out.println("2.获取成员变量注解信息");
        Field[] declaredFields = cla.getDeclaredFields();
        for (Field field : declaredFields) {
            Annotation[] fieldDeclaredAnnotations = field.getDeclaredAnnotations();
            for (Annotation fieldAnnotation : fieldDeclaredAnnotations) {
                if (fieldAnnotation instanceof Kiwipeach) {
                    String fileName = field.getName();
                    String annotaionValue = ((Kiwipeach) fieldAnnotation).value();
                    System.out.println("成员属性:" + fileName + " 成员注解值:" + annotaionValue);
                }
            }
        }
        //获取类中成员方法的注解值
        System.out.println("3.获取类成员方法注解信息");
        Method[] declaredMethods = cla.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Annotation[] methodDeclaredAnnotations = method.getDeclaredAnnotations();
            //方法的注解获取方法
            for (Annotation methodAnnotation : methodDeclaredAnnotations) {
                if (methodAnnotation instanceof Kiwipeach) {
                    String methodName = method.getName();
                    String annotaionValue = ((Kiwipeach) methodAnnotation).value();
                    System.out.println("方法名:" + methodName + " 方法上的注解值:" + annotaionValue);
                }
            }
            //方法参数注解获取方法
            System.out.println("4.获取类成员方法参数注解信息");
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            String methodName = method.getName();
            System.out.println("方法名:" + methodName);
            for (int i = 0; i < parameterAnnotations.length; i++) {
                if (parameterAnnotations[i][0] instanceof Kiwipeach) {
                    String annotaionValue = ((Kiwipeach) parameterAnnotations[i][0]).value();
                    System.out.println(" 方法上的参数值:" + annotaionValue);
                }
            }

        }
    }

}
