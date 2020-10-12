package com.sise.school.teach.utils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁工具
 *
 * @author idea
 * @data 2018/11/28
 */
public class LockUtil {

    public static ReentrantLock reentrantLock=new ReentrantLock();

    public static void main(String[] args) {
        final LockUtil test=new LockUtil();
        new Thread(){
            @Override
            public void run(){
                test.insert(Thread.currentThread());
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                test.insert(Thread.currentThread());
            }
        }.start();
    }

    public void insert(Thread thread){
        reentrantLock.lock();
        try {
            System.out.println(thread.getName()+"获取到了锁");
            for (int i=0;i<10;i++) {
            }
            System.out.println(thread.getName()+"释放了锁");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

}
