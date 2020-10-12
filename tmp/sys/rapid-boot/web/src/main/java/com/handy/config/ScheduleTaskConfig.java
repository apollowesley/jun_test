package com.handy.config;

import cn.hutool.core.date.DateUtil;
import com.handy.service.entity.task.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


/**
 * @author handy
 * @Description: {定时任务}
 * @date 2019/9/6 11:20
 */
@Configuration
public class ScheduleTaskConfig {
    private Logger logger = LoggerFactory.getLogger(ScheduleTaskConfig.class);
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 存放所有启动定时任务对象
     */
    private HashMap<String, ScheduledFuture> scheduleMap = new HashMap<>();

    /**
     * 动态设置定时任务方法
     *
     * @param scheduleTask
     */
    public void startCron(List<ScheduleTask> scheduleTask) {
        try {
            //遍历所有库中动态数据，根据库中class取出所属的定时任务对象进行关闭，每次都会把之前所有的定时任务都关闭，根据新的状态重新启用一次，达到最新配置
            for (ScheduleTask task : scheduleTask) {
                ScheduledFuture<?> scheduledFuture = scheduleMap.get(task.getClazzPathName());
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(true);
                }
            }
            //遍历库中数据，之前已经把之前所有的定时任务都停用了，现在判断库中如果是启用的重新启用并读取新的数据，把开启的数据对象保存到定时任务对象中以便下次停用
            for (ScheduleTask task : scheduleTask) {
                if (task.getStatus()) {
                    //开启一个新的任务，库中存储的是全类名（包名加类名）通过反射成java类，读取新的时间
                    ScheduledFuture<?> future = threadPoolTaskScheduler.schedule((Runnable) Class.forName(task.getClazzPathName()).newInstance(), new CronTrigger(task.getCron()));
                    scheduleMap.put(task.getClazzPathName(), future);
                }
            }
            logger.info("定时任务重新启动,执行时间为:" + DateUtil.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}