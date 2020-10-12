package com.handy.task;

import com.handy.config.ScheduleTaskConfig;
import com.handy.service.entity.task.ScheduleTask;
import com.handy.service.service.task.IScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/6 11:32
 */
@Component
public class TaskRunner implements ApplicationRunner {
    @Autowired
    private IScheduleTaskService scheduleTaskService;
    @Autowired
    private ScheduleTaskConfig scheduleTaskConfig;

    /**
     * 启动项目时候自动运行定时任务
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ScheduleTask> list = scheduleTaskService.list();
        scheduleTaskConfig.startCron(list);
    }
}
