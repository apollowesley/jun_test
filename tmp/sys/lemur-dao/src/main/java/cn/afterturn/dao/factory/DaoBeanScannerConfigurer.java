package cn.afterturn.dao.factory;

import java.lang.annotation.Annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import cn.afterturn.dao.annotation.IDao;
import cn.afterturn.dao.proxy.JdbcDaoProxyHandler;
import cn.afterturn.dao.resource.SQLLoader;

/**
 * 扫描配置文件
 * 
 * @author JueYue
 * @date 2014年11月15日 下午9:48:31
 */
public class DaoBeanScannerConfigurer implements BeanDefinitionRegistryPostProcessor {
    /**
     * ,; \t\n
     */
    private String                      daoPackage;
    /**
     * ,; \t\n
     */
    private String                      sqlPackage;
    /**
     * 默认是IDao,推荐使用Repository
     */
    private Class<? extends Annotation> annotation = IDao.class;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
                                                                                  throws BeansException {
        /**
         * 首先注册代理类                                                                       
         */
        registerRequestProxyHandler(registry);

        DaoClassPathMapperScanner scanner = new DaoClassPathMapperScanner(registry,annotation);
        /**
         * 加载Dao层接口
         */
        scanner.scan(StringUtils.tokenizeToStringArray(this.daoPackage,
            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
        /**
         * 加载sql
         */
        new SQLLoader().load(StringUtils.tokenizeToStringArray(this.sqlPackage,
            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                                                                                   throws BeansException {
    }

    /**
     * RequestProxyHandler 手工注册代理类,减去了用户配置XML的烦恼
     * 
     * @param registry
     */
    private void registerRequestProxyHandler(BeanDefinitionRegistry registry) {
        GenericBeanDefinition jdbcDaoProxyDefinition = new GenericBeanDefinition();
        jdbcDaoProxyDefinition.setBeanClass(JdbcDaoProxyHandler.class);
        registry.registerBeanDefinition("jdbcDaoProxyHandler", jdbcDaoProxyDefinition);
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public void setSqlPackage(String sqlPackage) {
        this.sqlPackage = sqlPackage;
    }

    public void setAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

}
