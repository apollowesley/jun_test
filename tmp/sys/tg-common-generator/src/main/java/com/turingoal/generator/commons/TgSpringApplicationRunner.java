package com.turingoal.generator.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 容器启动完成执行
 */
@Component
public class TgSpringApplicationRunner implements ApplicationRunner {
    private final Logger log = LogManager.getLogger(TgSpringApplicationRunner.class);
    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private TgSpringPropertiesSystem tgSpringPropertiesSystem;
    @Autowired
    private TgCodeGenerator tgCodeGeneratorService;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.info("<<<<<<<<<<<< 系统启动完成  " + applicationName + " version：" + tgSpringPropertiesSystem.getAppVerson() + " >>>>>>>>>>>>>>");
        tgCodeGeneratorService.generate(); // 生成代码
    }
}