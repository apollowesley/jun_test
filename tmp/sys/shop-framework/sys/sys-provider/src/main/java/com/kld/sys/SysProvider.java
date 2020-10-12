package com.kld.sys;


import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by anpushang on 2016/3/13.
 */
public class SysProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        System.out.println("★★★★★★SysProvider服务启动成功★★★★★★");
        synchronized (SysProvider.class)
        {
            while (true)
                try
                {
                    SysProvider.class.wait();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
        }

    }
}
