package com.kind.samples.core.jvm.heap;

/**
 * <pre>
 * 1.对象可以在被GC时自我拯救.
 * 2.这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次.
 * </pre>
 * Created by weiguo.liu on 2016/10/21.
 */
public class GCFinalizeContext {

    public static GCFinalizeContext LIVE_HOOK;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("execute finalize method!");
        GCFinalizeContext.LIVE_HOOK = this;
    }

    public static void isAlive() {
        if (LIVE_HOOK != null) {
            System.out.println("I'm still alive");
        } else
            System.out.println("I'm dead");
    }

    public void testHeap() {
        Object obj = new Object();
        System.gc();
        System.out.println("gc");
        obj = new Object();
        obj = new Object();
        System.gc();
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        LIVE_HOOK = new GCFinalizeContext();
        isAlive();

        System.out.println("第一次进行GC");
        LIVE_HOOK = null;
        System.gc();
        Thread.sleep(2000);
        isAlive();

        System.out.println("第二次进行GC");
        //再次调用
        LIVE_HOOK = null;
        System.gc();
        Thread.sleep(2000);
        isAlive();

        new GCFinalizeContext().testHeap();
    }
}
