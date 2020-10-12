jfinal-scheduler插件

基于cron4j以及ScheduledThreadPoolExecutor实现的简单任务调度插件

插件启用方式(采用builder模式构建)：
```
        SchedulerPlugin schedulerPlugin = SchedulerPlugin.builder()
                                                         .scheduledThreadPoolSize(10)
                                                         .enableAnnotationScan("com.test,com.abc")
                                                         .enableConfigFile("job.properties")
                                                         .build();
        me.add(schedulerPlugin);
```
使用方法：
在JFinal的Config配置文件中配置（编码加载）
```
    @Override
    public void configPlugin(Plugins me) {
        SchedulerPlugin sp = new SchedulerPlugin();
        Runnable task = new TestTask();
        //每隔10秒执行一次
        //sp.fixedDelaySchedule(task, 10);
        //sp.fixedRateSchedule(task, 10);
        //每隔1分钟执行一次
        sp.cronSchedule(task, "* * * * *");
        me.add(sp);
```

在代码中通过注解配置
```
    @Cron("0 3 * * *")
    public class XmDataSyncJob implements Runnable {
        private static final Log LOG = Log.getLog(XmDataSyncJob.class);
    ……
    @FixedDelay(period = 60,initialDelay=60)
    public class MailSendJob implements Runnable {
    ……
    @FixedRate(period = 120,initialDelay=90)
    public class SmsSendJob implements Runnable {
    ……
```

在JFinal的Config配置文件中配置（通过配置文件加载）
job.properties
```
#不需要在部署时调整的定时任务，可使用注解自动加载
#是否启用该任务
#testJob.enable=true
#任务类名
#testJob.class=com.wellbole.web.core.job.TestJob
#任务类型以及表达式
#testJob.type=cron
#testJob.expr=16 17 * * Mon

#每隔10秒（每分钟6次）执行一次
#testJob.type=fixedRate
#testJob.period=10
#testJob.initialDelay=32

#每隔10秒(任务一个接着一个)执行一次
#testJob.type=fixedDelay
#testJob.period=10
#testJob.initialDelay=32

#job1.class=x.y.z.Runnable
```
