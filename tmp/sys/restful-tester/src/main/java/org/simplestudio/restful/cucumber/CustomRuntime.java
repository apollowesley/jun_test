package org.simplestudio.restful.cucumber;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.simplestudio.restful.httpclient.cache.FeatureCache;
import org.simplestudio.restful.util.PropertyUtil;
import org.simplestudio.restful.util.RestUtil;

import cucumber.api.StepDefinitionReporter;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Glue;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeGlue;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.UndefinedStepsTracker;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.xstream.LocalizedXStreams;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

public class CustomRuntime extends Runtime {
    private Logger                      logger                = Logger
            .getLogger(CustomRuntime.class);
    private final ClassLoader           classLoader;
    private final RuntimeOptions        runtimeOptions;
    private final ResourceLoader        resourceLoader;
    private final UndefinedStepsTracker undefinedStepsTracker = new UndefinedStepsTracker();

    public CustomRuntime(ResourceLoader resourceLoader, ClassFinder classFinder,
            ClassLoader classLoader, RuntimeOptions runtimeOptions) {
        super(resourceLoader, classFinder, classLoader, runtimeOptions);
        this.classLoader = classLoader;
        this.runtimeOptions = runtimeOptions;
        this.resourceLoader = resourceLoader;
    }

    /**
     * 设置要执行的features以及顺序
     * @param features
     */
    private void setRunFeatures(List<CucumberFeature> features) {
        //对获取到的进行features进行加工，根据需要进行组合
        String runOrderList = PropertyUtil.getProperty("run.feature.orderlist");
        if (!RestUtil.isBlank(runOrderList)) {
            //解析现有的feature，取出路径
            Map<String, CucumberFeature> orderMap = new HashMap<String, CucumberFeature>();
            for (CucumberFeature feature : features) {
                orderMap.put(feature.getGherkinFeature().getName(), feature);
            }

            //清空原有的
            features.clear();

            String[] runOrderArr = runOrderList.split(",");
            for (String name : runOrderArr) {
                CucumberFeature feature = orderMap.get(name.trim());
                if (feature != null) {
                    features.add(feature);
                }
                else {
                    logger.warn("根据feature名称[" + name + "]找不到对应的feature文件，请确认配置是否正确！]");
                }
            }
        }
    }

    @Override
    public void run() throws IOException {
        List<CucumberFeature> features = this.runtimeOptions.cucumberFeatures(this.resourceLoader);
        setRunFeatures(features);

        Formatter formatter = this.runtimeOptions.formatter(this.classLoader);
        Reporter reporter = this.runtimeOptions.reporter(this.classLoader);
        StepDefinitionReporter stepDefinitionReporter = this.runtimeOptions
                .stepDefinitionReporter(this.classLoader);

        Glue glue = new RuntimeGlue(this.undefinedStepsTracker, new LocalizedXStreams(classLoader));
        glue.reportStepDefinitions(stepDefinitionReporter);

        logger.info("feature总数：" + features.size());

        int index = 1;
        int size = features.size();
        for (CucumberFeature cucumberFeature : features) {
            logger.info("第" + (index++) + "/" + size + "个Feature开始执行...");
            cucumberFeature.run(formatter, reporter, this);
            //当前feature设置的缓存，按照feature独立运行的原则，要清空，避免跨feature的依赖造成不必要的问题
            FeatureCache.clear();
        }

        formatter.done();
        formatter.close();
        printSummary();
    }
}
