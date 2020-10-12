package org.nature.framework.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

public class JobDemo implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
	}
	
	public static void main(String[] args) throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		//JobDetail jobDetail = new Job(JobDemo.class).with
		
	}
	
}
