package com.site.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.site.biz.handler.JobBuilderFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution // 不允许并发执行
public class MyQuartzJobBean extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(MyQuartzJobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        Trigger trigger = jobexecutioncontext.getTrigger();
        // String triggerName = trigger.getKey().getName();
        String group = trigger.getKey().getGroup();
        JobBuilderFactory context = getApplicationContext(jobexecutioncontext).getBean("jobBuilderFactory", JobBuilderFactory.class);
        try {
            context.execute(group);
        } catch (Exception e) {
            log.info("#MyQuartzJobBean.executeInternal , group={} error message=[{}]", group, e.getLocalizedMessage());
        }
    }

    private ApplicationContext getApplicationContext(final JobExecutionContext jobexecutioncontext) {
        try {
            return (ApplicationContext) jobexecutioncontext.getScheduler().getContext().get("applicationContextKey");
        } catch (SchedulerException e) {
            log.error("#MyQuartzJobBean.getApplicationContext , error message={}", e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

}
