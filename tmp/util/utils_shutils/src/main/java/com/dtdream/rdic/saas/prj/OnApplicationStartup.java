package com.dtdream.rdic.saas.prj;

import com.dtdream.rdic.saas.utils.BeanUtils;
import com.dtdream.rdic.saas.utils.ConfigUtils;
import com.dtdream.rdic.saas.utils.KitUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ozz8 on 2015/06/10.
 */
@Component
public class OnApplicationStartup implements BeanPostProcessor,ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    protected ConfigUtils configuration;

    private static List<String> classset;
    static {
        classset = new ArrayList<>(1);
        classset.add(CorsFilter.class.getName());
    }

    public static ApplicationContext objContext;
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (null != objContext)
            return;
        objContext = contextRefreshedEvent.getApplicationContext();

        /**
         * 内置需要配置的类
         */
        ClassLoader loader = this.getClass().getClassLoader();
        Iterator<String> it = classset.iterator();
        String classname;
        while (it.hasNext()) {
            classname = it.next();
            if (KitUtils.check(classname).failure())
                continue;
            BeanUtils.invoke(loader, classname, "config", configuration.getConfigurations());
        }

        /**
         * 配置的需要自动更新的类
         */
        Object clsset = configuration.config("app.start.config");
        if (null == clsset || (!(clsset instanceof List)) || (((List) clsset).size() <= 0))
            return;
        it = ((List) clsset).iterator();
        while (it.hasNext()) {
            classname = it.next();
            if (KitUtils.check(classname).failure())
                continue;
            BeanUtils.invoke(loader, classname, "config", configuration.getConfigurations());
        }

        return;
    }
}
