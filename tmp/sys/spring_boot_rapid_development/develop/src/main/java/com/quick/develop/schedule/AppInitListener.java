package com.quick.develop.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@Component
@Slf4j
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}