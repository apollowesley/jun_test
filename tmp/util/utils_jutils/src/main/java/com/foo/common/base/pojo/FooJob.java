package com.foo.common.base.pojo;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.foo.common.base.utils.FooUtils;

@DisallowConcurrentExecution
public class FooJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		FooUtils.info("trigger once");
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		FooUtils.info("jobDataMap id is" + jobDataMap.get("id"));
		try {
			FooUtils.info("Sleep now");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
