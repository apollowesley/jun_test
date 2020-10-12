package com.niki.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: trade
 * @Package: com
 * @ClassName: StartJob
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/4/30 15:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Component
@Slf4j
public class StartJob implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("开始启动所有的定时任务！！！");
        init();
        log.info("启动所有的定时任务结束！！！");
    }

    @Autowired
    private QuartzService quartzService;

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     *  启动任务
                     */
                    String jobName = "com.DynamicJob";
                    String cron = "0/10 * * * * ?";
                    String classPath = "com.niki.job.DynamicJob";
                    String JOB_GROUP_NAME = "com-nikijava-job";
                    quartzService.deleteJob(jobName, JOB_GROUP_NAME);
                    quartzService.addJob((Class<? extends QuartzJobBean>) Class.forName(classPath), jobName, JOB_GROUP_NAME, cron);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("monitor----StartJob异常信息==={}", e.getStackTrace());
                }

            }
        }).start();


    }
}