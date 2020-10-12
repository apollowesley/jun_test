package com.navercorp.pinpoint.plugin.xxljob;

import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplate;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformTemplateAware;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPlugin;
import com.navercorp.pinpoint.bootstrap.plugin.ProfilerPluginSetupContext;
import com.navercorp.pinpoint.plugin.xxljob.transform.FutureTransform;
import com.navercorp.pinpoint.plugin.xxljob.transform.JobExecutorTransform;
import com.navercorp.pinpoint.plugin.xxljob.transform.JobThreadTransform;

/**
 * 任何Pinpoint Profiler插件都必须实现ProfilerPlugin接口
 * 然后实现里面的setup方法
 *
 * @author YY
 * @version 1.0
 * @date 2019-05-17 17:53
 */
public class XxlJobPlugin implements ProfilerPlugin, TransformTemplateAware {

    private TransformTemplate transformTemplate;

    @Override
    public void setTransformTemplate(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }

    @Override
    public void setup(ProfilerPluginSetupContext context) {
        //xxl job的的执行器，初始化了JobThread，监听registJobThread方法
        transformTemplate.transform("com.xxl.job.core.executor.XxlJobExecutor", new JobExecutorTransform());
        //异步线程JobThread，监听里面的run
        transformTemplate.transform("com.xxl.job.core.thread.JobThread", new JobThreadTransform());
        //JobThread线程调用了FutureTask，监听get方法
        transformTemplate.transform("java.util.concurrent.FutureTask", new FutureTransform());
    }
}
