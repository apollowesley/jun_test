package com.niki.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ProjectName: trade-2019-05-16
 * @Package: com.job
 * @ClassName: HuaxiaCutDayJob
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/5/13 11:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Slf4j
public class DemoJob extends QuartzJobBean {
    /**
     * 可以直接注入bean对象使用
     * @param jobExecutionContext
     * @throws JobExecutionException
     *
     * @Autowired
     * private PayOrderService payOrderService;
     *
     */

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("DemoJob------->>> ====。。线程id==={}，时间是====={}====",Thread.currentThread().getName(),System.currentTimeMillis());
    }
}