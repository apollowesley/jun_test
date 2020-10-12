package com.kld.sys;

import com.kld.promotion.api.ISellCtgyService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by anpushang on 2016/3/13.
 */
public class SellCtgyTest {
    private ClassPathXmlApplicationContext context;

    @Test
    public void test01()throws Exception{
        context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();
        ISellCtgyService sellCtgyService = (ISellCtgyService)context.getBean("sellCtgyService") ;
        System.out.println(sellCtgyService.getString("归咎"));
    }


}
