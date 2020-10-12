package com.kld.promotion;


import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by anpushang on 2016/3/13.
 */
public class PromotionProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        System.out.println("★★★★★★PromotionProvider服务启动成功★★★★★★");
        synchronized (PromotionProvider.class)
        {
            while (true)
                try
                {
                    PromotionProvider.class.wait();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
        }

    }
}
