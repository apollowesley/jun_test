package org.simplestudio.restful.cucumber;

import org.simplestudio.restful.RunCukes;
import org.simplestudio.restful.util.PropertyUtil;
import org.simplestudio.restful.util.RestUtil;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.formatter.PluginFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;

public class CucumberHelper {
    // 用于将命令诸如--plugin html:target/html解析成对应的plugin object
    private static final PluginFactory PLUGIN_FACTORY = new PluginFactory();

    private static Runtime createRuntime(ResourceLoader resourceLoader, ClassLoader classLoader,
            RuntimeOptions runtimeOptions) throws Exception {
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        return new CustomRuntime(resourceLoader, classFinder, classLoader, runtimeOptions);
    }

    /**
     * 初始化插件
     * @param runtimeOptions
     */
    private static void setPlugin(RuntimeOptions runtimeOptions) {
        String plugins = PropertyUtil.getProperty("add.plugin.list");
        if (!RestUtil.isBlank(plugins)) {
            String[] pluginArr = plugins.split(",");
            for (String plugin : pluginArr) {
                runtimeOptions.addPlugin(PLUGIN_FACTORY.create(plugin));
            }
        }
    }

    /**
     * 
     * @param clazz
     * @param subName
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void run() throws Exception {
        Class clazz = RunCukes.class;
        ClassLoader classLoader = clazz.getClassLoader();
        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
        RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();

        String moduleName = PropertyUtil.getProperty("moduleName");
        //设置是否需要某个子模块
        if (!RestUtil.isBlank(moduleName)) {
            runtimeOptions.getFeaturePaths().clear();
            runtimeOptions.getFeaturePaths()
                    .add("classpath:" + RestUtil.getPackagePath(RunCukes.class) + "/" + moduleName);
        }

        //处理插件
        setPlugin(runtimeOptions);

        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        Runtime runtime = createRuntime(resourceLoader, classLoader, runtimeOptions);
        //执行解析
        runtime.run();
    }
}
