package com.kind.samples.core.threads;

public class TraditionalThread {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 通过Thread实现线程
         */
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread1:"
                            + Thread.currentThread().getName());
                }
            }
        };
        thread1.start();
        /**
         * 通过Runnable接口实现线程
         */
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread2:"
                            + Thread.currentThread().getName());
                }
            }
        });
        thread2.start();

        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("runnable:"
                            + Thread.currentThread().getName());
                }
            }
        }
        ) {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread :"
                            + Thread.currentThread().getName());

                }
            }
        }.start();
    }
}
