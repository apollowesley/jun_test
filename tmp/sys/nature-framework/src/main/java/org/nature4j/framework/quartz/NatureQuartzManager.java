package org.nature4j.framework.quartz;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.util.JedisUtil;
import org.nature4j.framework.util.SystemUtil;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

public class NatureQuartzManager{
	private static Logger LOGGER = LoggerFactory.getLogger(NatureQuartzManager.class);
	private static Scheduler scheduler;
	public static void startQuartzScheduler(){
			try {
				scheduler = readyAndStartScheduler();
				if (ConfigHelper.getQuartzCluster()) {
					vieStartScheduler(scheduler);
				}else{
					scheduler.start();
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
	}
	private static Scheduler readyAndStartScheduler() {
		try {
			Map<String, QuartzJobBean> quartzJobMap = QuartzJobHelper.getQuartzJobMap();
			Set<Entry<String, QuartzJobBean>> quartzJobs = quartzJobMap.entrySet();
			for (Entry<String, QuartzJobBean> quartzJob : quartzJobs) {
				QuartzJobBean quartzJobBean = quartzJob.getValue();
				String clsName = quartzJob.getKey();
				Map<String, QuartzMethodBean> quartzMethods = quartzJobBean.getMethods();
				Set<Entry<String, QuartzMethodBean>> QuartzMethodBeans = quartzMethods.entrySet();
				for (Entry<String, QuartzMethodBean> quartzMethodBean : QuartzMethodBeans) {
					QuartzMethodBean methodBean = quartzMethodBean.getValue();
					String methodName = quartzMethodBean.getKey();
					String cron = methodBean.getCron();
						JobDetail job = JobBuilder.newJob(QuartzJob.class).withIdentity(methodName, clsName).build();
						Trigger trigger = TriggerBuilder.newTrigger().withIdentity(methodName, clsName)
								.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
						scheduler.scheduleJob(job, trigger);
				}
			}
		} catch (SchedulerException e) {
			LOGGER.error("startQuartzScheduler error");
			throw new RuntimeException("startQuartzScheduler error");
		}
		return scheduler;
	}
	public static void initScheduler(){
		try {
			String clusterId = "quartz_host:"+SystemUtil.getHostAddress()+":"+System.currentTimeMillis();
			StdSchedulerFactory sf = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName",clusterId);
			props.put("org.quartz.threadPool.threadCount",ConfigHelper.getQuartzThreadCount());
			sf.initialize(props);
			scheduler = sf.getScheduler();
		} catch (SchedulerException e) {
			LOGGER.error("getScheduler error");
			throw new RuntimeException("getScheduler error");
		}
	}
	private static void vieStartScheduler(final Scheduler scheduler) {
		
			Thread thread = new Thread(){public void run() {
				try {
					while(true){
						ShardedJedis jedis = JedisUtil.getShardedJedis();
						String clusterId = scheduler.getSchedulerName();
						String currentClusterId = jedis.get("quartz_scheduler_clusterId");
						Long setnx = jedis.setnx("quartz_scheduler_clusterId", clusterId);
						if (clusterId.equals(currentClusterId)||setnx==1) {
							jedis.setex("quartz_scheduler_clusterId",ConfigHelper.getQuartzLiveCycle(), clusterId);
							if (!scheduler.isStarted()) {
								scheduler.start();
							}else if (scheduler.isInStandbyMode()) {
								scheduler.start();
							}else{
							}
						}else{
							if (scheduler.isStarted()) {
								scheduler.standby();
							}
						}
						Thread.sleep(ConfigHelper.getQuartzLiveTest()*1000);
					}
				} catch (Exception e) {
					LOGGER.error("reallyStartScheduler error");
					throw new RuntimeException("reallyStartScheduler error"); 
				}
			};};
			thread.start();
	}
	
	public static void stopAll(){
		if (scheduler!=null) {
			try {
				scheduler.shutdown(true);
			} catch (SchedulerException e) {
				try {
					scheduler.shutdown();
				} catch (SchedulerException e1) {
					try {
						scheduler.clear();
					} catch (SchedulerException e2) {
						LOGGER.error("Scheduler stopAll error");
						throw new RuntimeException("Scheduler stopAll error"); 
					}
				}
			}
		}
	}
	
}
