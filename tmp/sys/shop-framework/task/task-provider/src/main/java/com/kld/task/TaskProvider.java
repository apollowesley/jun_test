package com.kld.task;


import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by anpushang on 2016/3/13.
 */
public class TaskProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        System.out.println("★★★★★★TaskProvider服务启动成功★★★★★★");
        synchronized (TaskProvider.class)
        {
            while (true)
                try
                {
                    TaskProvider.class.wait();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
        }

    }
}
