package com.niki.job;

import com.alibaba.druid.pool.DruidDataSource;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;

/**
 * @ProjectName: trade-2019-05-16
 * @Package: com
 * @ClassName: QuartzConfiguration
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/5/13 15:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {

    /**
     * 继承org.springframework.scheduling.quartz.SpringBeanJobFactory
     * 实现任务实例化方式
     */
    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
            ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }

        /**
         * 将job实例交给spring ioc托管
         * 我们在job实例实现类内可以直接使用spring注入的调用被spring ioc管理的实例
         *
         * @param bundle
         * @return
         * @throws Exception
         */
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            /**
             * 将job实例交付给spring ioc
             */
            beanFactory.autowireBean(job);
            return job;
        }
    }

    /**
     * 配置任务工厂实例
     *
     * @return
     */
    @Bean
    public JobFactory jobFactory() {
        /**
         * 采用自定义任务工厂 整合spring实例来完成构建任务*/
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        return jobFactory;
    }



    /************************************************单机版打开以下方法,把多机的注释掉********************************************************************/
    /**
     * @method
     * @description  单机版
     * @date: 2019/8/28 0028 19:39
     * @author: Niki Zheng
     *@param
     * @return
     */
    /* @Bean(destroyMethod = "destroy", autowire = Autowire.NO)
   public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待2秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(2);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //多机版设置数据源，使用与项目统一数据源 或者加上注解@QuartzDataSource 并且名字跟配置文件名字一样的数据源
        //单机版不设置数据源
        schedulerFactoryBean.setNonTransactionalDataSource(null);
        //设置上下文spring bean name
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }*/




    /************************************************多机版跟项目共用数据源打开以下方法，并注释其他2个方法********************************************************************/

    /**
     * 配置任务调度器
     * 使用项目数据源作为quartz数据源
     *
     * @param jobFactory 自定义配置任务工厂
     * @param dataSource 数据源实例
     * @return
     * @throws Exception
     */
    /*@Bean(destroyMethod = "destroy", autowire = Autowire.NO)
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待2秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(2);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //多机版设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);
        //设置上下文spring bean name
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }*/





/************************************************多机版,使用自己的数据源打开以下方法，并注释其他2个方法********************************************************************/
    /**
     * @method
     * @description 多机版，使用自己的数据源
     * @date: 2019/8/28 0028 19:40
     * @author: Niki Zheng
     *@param
     * @return
     */
    @Bean(destroyMethod = "destroy", autowire = Autowire.NO)
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource qzdataSource) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待2秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(2);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //多机版设置数据源，使用与项目统一数据源 或者加上注解@QuartzDataSource 并且名字跟配置文件名字一样的数据源
        schedulerFactoryBean.setDataSource(qzdataSource);
        //设置上下文spring bean name
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }

    @Bean("qzdataSource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://192.168.3.13:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8");
        druidDataSource.setUsername("erp");
        druidDataSource.setPassword("erp2019");
        druidDataSource.setDbType("mysql");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return druidDataSource;
    }


}