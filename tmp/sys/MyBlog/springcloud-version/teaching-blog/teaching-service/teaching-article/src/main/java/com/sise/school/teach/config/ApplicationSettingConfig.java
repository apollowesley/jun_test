package com.sise.school.teach.config;

import org.sise.idea.task.TaskSwitch;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 配置自定义缓存同步器
 * @author idea
 * @data 2018/11/29
 */
@Component
@Order(1)
public class ApplicationSettingConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        TaskSwitch applicationTaskSwitch = new TaskSwitch();
        applicationTaskSwitch.timeUnit(TimeUnit.SECONDS)
                .delayTime(2)
                .period(15)
                .threadCount(1)
                .startTask("config/ApplicationSetting-conf.xml");
    }
}
