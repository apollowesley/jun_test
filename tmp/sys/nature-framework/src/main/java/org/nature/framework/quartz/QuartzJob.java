package org.nature.framework.quartz;

import java.lang.reflect.Method;

import org.nature.framework.util.ReflectionUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class QuartzJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		String methodName = jobKey.getName();
		String clsName = jobKey.getGroup();
		QuartzJobBean jobBean = QuartzJobHelper.getQuartzJobBean(clsName);
		Object quartzJobObject = jobBean.getQuartzJobObject();
		Method method = jobBean.getMethod(methodName);
		ReflectionUtil.invokeMethod(quartzJobObject, method);
	}
}
