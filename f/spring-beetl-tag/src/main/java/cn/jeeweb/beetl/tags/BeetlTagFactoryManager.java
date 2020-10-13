package cn.jeeweb.beetl.tags;

import cn.jeeweb.beetl.tags.annotation.BeetlTagName;
import cn.jeeweb.beetl.tags.utils.SpringContextUtils;
import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.beetl.ext.spring.SpringBeanTagFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.tags.config
 * @title:
 * @description: 标签处理
 * @author: 王存见
 * @date: 2018/8/7 15:10
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
public class BeetlTagFactoryManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    public BeetlTagFactoryManager(){
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        SpringContextUtils.setApplicationContext(this.applicationContext);
    }
    public BeetlTagFactoryManager(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        SpringContextUtils.setApplicationContext(this.applicationContext);
    }

    public Map<String, TagFactory> loadFactorys(String... basePackages) {
        GenericApplicationContext context = new GenericApplicationContext(applicationContext);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
        // 扫描标签
        if (basePackages!=null&& basePackages.length > 0){
            scanner.scan(basePackages);
        }
        int length = scanner.scan("cn.jeeweb.beetl.tags");
        context.refresh();
        Map<String, TagFactory> tags = new HashMap<String, TagFactory>();
        Map<String, Tag> beans = context.getBeansOfType(Tag.class);
        for (String beanName : beans.keySet()) {
            // 读取自定义标签名
            BeetlTagName tagAnno = beans.get(beanName).getClass().getAnnotation(BeetlTagName.class);
            String tagName = tagAnno != null ? tagAnno.value() : beanName;
            tags.put(tagName, toSpringBeanTagFactory(beanName,context));
        }
        return tags;
    }

    private SpringBeanTagFactory toSpringBeanTagFactory(String beanName,GenericApplicationContext context ) {
        SpringBeanTagFactory springBeanTagFactory = new SpringBeanTagFactory();
        springBeanTagFactory.setApplicationContext(context);
        springBeanTagFactory.setName(beanName);
        return springBeanTagFactory;
    }
}
