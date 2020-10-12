package net.myscloud.pandora.test.aop;

import java.io.IOException;


/**
 * @author Jin Xiaotian
 * @Description: TODO
 * @date 2015/10/27 15:51
 */
public class TestBean {

    public void halloAop() {
        System.out.println("Hello Aop");
    }

    public void halloAop2() {
        System.out.println("Hello Aop2");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        AopClassLoader classLoader = new AopClassLoader(Thread.currentThread().getContextClassLoader());
        Class clazz = classLoader.loadClass("net.myscloud.pandora.test.aop.TestBean_Tmp");
        TestBean bean = (TestBean) clazz.newInstance();
        bean.halloAop();
        bean.halloAop2();
    }
}
