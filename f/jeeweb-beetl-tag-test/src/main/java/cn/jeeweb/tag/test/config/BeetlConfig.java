package cn.jeeweb.tag.test.config;

import cn.jeeweb.beetl.tags.BeetlTagFactoryManager;
import org.beetl.core.TagFactory;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.generator.config
 * @title:
 * @description: Beetl配置
 * @author: 王存见
 * @date: 2018/8/7 11:31
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Configuration(value = "beetlConfiguration")
public class BeetlConfig {

    @Value("${beetl.templatesPath}")
    private String templatesPath;//模板根目录 ，比如 "templates"

    @Bean
    public BeetlTagFactoryManager beetlTagFactoryManager(){
        BeetlTagFactoryManager beetlTagFactoryManager = new BeetlTagFactoryManager();
        return beetlTagFactoryManager;
    }
    @Bean(name = "beetlConfig")
    public BeetlGroupUtilConfiguration beetlConfig(BeetlTagFactoryManager beetlTagFactoryManager) {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        //获取Spring Boot 的ClassLoader
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader==null){
            loader = BeetlConfig.class.getClassLoader();
        }
        // beetlGroupUtilConfiguration.setConfigProperties(extProperties);//额外的配置，可以覆盖默认配置，一般不需要
        ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,
                templatesPath);
        beetlGroupUtilConfiguration.setResourceLoader(cploder);
        // 添加标签
        beetlGroupUtilConfiguration.setTagFactorys(beetlTagFactoryManager.loadFactorys());
        beetlGroupUtilConfiguration.init();
        //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
        beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
        return beetlGroupUtilConfiguration;

    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");;
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

}
