package com.kld.order;


import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by anpushang on 2016/3/13.
 */
public class OrderProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        System.out.println("★★★★★★OrderProvider服务启动成功★★★★★★");
        synchronized (OrderProvider.class)
        {
            while (true)
                try
                {
                    OrderProvider.class.wait();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
        }

    }
}
