package com.niki.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: trade-2019-05-16
 * @Package: com
 * @ClassName: DynamicJob
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/5/14 9:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Slf4j
public class DynamicJob extends QuartzJobBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuartzService quartzService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String schedulerGroupName = jobExecutionContext.getJobDetail().getKey().getGroup();
        if (StringUtils.isEmpty(schedulerGroupName)) {
            throw new JobExecutionException("scheduleName 参数不能为空");
        } else {
            log.info("\r\n\r\n---load dynamic job for " + schedulerGroupName);
        }
        /**
         * 以下sql请换成你自己的sql，至少有job_name,cron,class_path,task_type,state 这几个字段， 状态0表示停止，1表示正常运行
         */
        String sql = "select job_name,cron,class_name,job_group,status from  job where job_group = ?  ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, schedulerGroupName);
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> job : list) {
                try {
                    String jobName = job.get("job_name").toString();
                    String cron = job.get("cron").toString();
                    String classPath = job.get("class_name").toString();
                    int state = Integer.valueOf(job.get("status").toString());
                    String taskType = job.get("job_group").toString();
                    dealJob(cron, classPath, state, jobName, taskType);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("DynamicJob executeInternal  for循环里面爆出异常，异常信息是==={}", e.getStackTrace());
                }
            }
        }
    }

    public void dealJob(String cron, String clazz, int status, String jobName, String JOB_GROUP_NAME) throws Exception {
        if (1 == status) {
            if (quartzService.isExist(jobName, JOB_GROUP_NAME)) {
                if (!quartzService.isSame(jobName, JOB_GROUP_NAME, cron)) {
                    try {
                        quartzService.deleteJob(jobName, JOB_GROUP_NAME);
                        quartzService.addJob((Class<? extends QuartzJobBean>) Class.forName(clazz), jobName, JOB_GROUP_NAME, cron);
                        log.info("DynamicJob update a job:{}", clazz);
                    } catch (Exception e) {
                        log.error("add job error :{}", clazz);
                        log.error(e.getMessage());
                    }
                }
            } else {
                quartzService.addJob((Class<? extends QuartzJobBean>) Class.forName(clazz), jobName, JOB_GROUP_NAME, cron);
                log.info("DynamicJob add a job:{}", clazz);
            }
        } else {
            if (quartzService.isExist(jobName, JOB_GROUP_NAME)) {
                quartzService.deleteJob(jobName, JOB_GROUP_NAME);
                log.info("DynamicJob delete a job:{}", clazz);
            }
        }
    }
}