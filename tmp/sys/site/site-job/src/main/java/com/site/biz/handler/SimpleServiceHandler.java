package com.site.biz.handler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.site.simple.service.SimpleJobService;

@Service("simpleServiceHandler")
public class SimpleServiceHandler extends DefaultJobHandler {

    private static final Logger log = LoggerFactory.getLogger(SimpleServiceHandler.class);

    @Autowired
    private SimpleJobService simpleJobService;

    @Override
    public String getClassName() {
        return "simpleServiceHandler";
    }

    @Override
    @PostConstruct
    public void addService() {
        log.info("#SimpleServiceHandler.class add to JobBuilderFactory.SERVERS");
        JobBuilderFactory.addServer(getClassName(), SimpleServiceHandler.class);
    }

    @Override
    public void execute() {
        log.info("#begin {}.execute()", getClassName());
        // 这里执行定时调度业务
        simpleJobService.execute();
        log.info("#end {}.execute()\r\t", getClassName());
    }

}
