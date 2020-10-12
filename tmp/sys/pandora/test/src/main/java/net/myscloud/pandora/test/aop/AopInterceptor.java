package net.myscloud.pandora.test.aop;

/**
 * @author Jin Xiaotian
 * @Description: TODO
 * @date 2015/10/27 15:50
 */
public class AopInterceptor {
    public static void beforeInvoke() {
        System.out.println("before");
    }

    public static void afterInvoke() {
        System.out.println("after");
    }

}
