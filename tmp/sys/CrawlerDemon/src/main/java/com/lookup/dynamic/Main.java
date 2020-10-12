/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic;

import com.lookup.dynamic.task.imp.WikiTask;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * A main class to start up the application.
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger("server");

    public static ApplicationContext context;

    public static void main(String[] args) throws Exception {

        PropertyConfigurator.configure("config/log4j.properties");
        context = new ClassPathXmlApplicationContext("applicationContext-main.xml");
       /* ProxyTask task = (ProxyTask) context.getBean("proxy");
        task.execute();*/

        WikiTask task = (WikiTask) context.getBean("wikiTask");
        task.execute();
    }
}
