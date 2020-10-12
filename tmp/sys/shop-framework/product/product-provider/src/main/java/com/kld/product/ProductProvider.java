package com.kld.product;


import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by anpushang on 2016/3/13.
 */
public class ProductProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        System.out.println("★★★★★★ProductProvider服务启动成功★★★★★★");
        synchronized (ProductProvider.class)
        {
            while (true)
                try
                {
                    ProductProvider.class.wait();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
        }

    }
}
