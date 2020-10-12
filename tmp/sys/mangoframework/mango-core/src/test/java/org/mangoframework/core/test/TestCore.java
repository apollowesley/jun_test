package org.mangoframework.core.test;

import java.lang.reflect.Modifier;

import org.junit.Test;
import org.mangoframework.core.dispatcher.ControllerMapping;

/**
 * @author zhoujingjie
 * @date 2016/4/22
 */
public class TestCore {

    @Test
    public void testName() {
        System.out.println(TestCore.class.getSimpleName());
    }

    @Test
    public void testPath() {
        ControllerMapping.initPackages("org.mangoframework.core.test");
    }

    @Test
    public void test3() throws ClassNotFoundException {
        Class<?> clazz = Class.forName(TestCore.class.getName());
        System.out.println(Modifier.isStatic(clazz.getModifiers()));
        System.out.println(Modifier.isAbstract(clazz.getModifiers()));
        System.out.println(Modifier.isFinal(clazz.getModifiers()));
        System.out.println(Modifier.isNative(clazz.getModifiers()));
        System.out.println(Modifier.isStrict(clazz.getModifiers()));
        System.out.println(Modifier.isTransient(clazz.getModifiers()));
        System.out.println(Modifier.isInterface(clazz.getModifiers()));
        System.out.println(Modifier.isPrivate(clazz.getModifiers()));
        System.out.println(Modifier.isPublic(clazz.getModifiers()));
        System.out.println(Modifier.isProtected(clazz.getModifiers()));
        System.out.println(Modifier.isVolatile(clazz.getModifiers()));
        System.out.println(Modifier.isSynchronized(clazz.getModifiers()));

    }

}
