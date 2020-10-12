package com.site.biz.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("jobBuilderFactory")
public class JobBuilderFactory {

    private static Logger log = LoggerFactory.getLogger(JobBuilderFactory.class);

    public static Map<String, Class<? extends DefaultJobHandler>> servers = new HashMap<String, Class<? extends DefaultJobHandler>>();

    @Autowired
    private ApplicationContext applicationContext;

    public static void addServer(String serverName, Class<? extends DefaultJobHandler> clazz) {
        log.info("##ServerBuilderContext.addServer({} , {})", serverName, clazz);
        servers.put(serverName, clazz);
    }

    private DefaultJobHandler getServer(String serverName) {
        Class<? extends DefaultJobHandler> clazz = servers.get(serverName);
        DefaultJobHandler processor = applicationContext.getBean(clazz);
        return processor;
    }

    public void execute(String serverName) {
        log.info("#ServerBuilderContext.execute({})", serverName);
        DefaultJobHandler server = getServer(serverName);
        try {
            server.execute();
        } catch (Exception e) {
            log.info("#ServerBuilderContext.execute({}) error  , [{}]", serverName, e.getLocalizedMessage());
        }
    }

}
