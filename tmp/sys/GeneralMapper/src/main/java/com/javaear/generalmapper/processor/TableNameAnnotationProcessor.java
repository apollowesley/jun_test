package com.javaear.generalmapper.processor;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.javaear.generalmapper.GeneralMapper;
import com.javaear.generalmapper.plugin.GeneralSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Map;

/**
 * @author aooer
 */
public class TableNameAnnotationProcessor implements BeanPostProcessor {

    private GeneralSqlInjector generalSqlInjector;

    private boolean plusInject = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 优先选择mybatis-plus配置进行动态扫描注入
     *
     * @param bean     bean
     * @param beanName beanName
     * @return bean
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            if (bean instanceof MybatisSqlSessionFactoryBean) {
                injectSql(((MybatisSqlSessionFactoryBean) bean).getObject().getConfiguration());
                plusInject = true;
            } else if (bean instanceof SqlSessionFactoryBean && !plusInject) {
                injectSql(((SqlSessionFactoryBean) bean).getObject().getConfiguration());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    /**
     * 扫描带@tableName注解的bean，动态注入sql
     *
     * @param configuration sqlsessionfactoryBean config
     */
    private void injectSql(Configuration configuration) {
        for (Map.Entry<String, Class<?>> type : configuration.getTypeAliasRegistry().getTypeAliases().entrySet()) {
            TableName tableName = type.getValue().getAnnotation(TableName.class);
            if (tableName != null) {
                String packageName = type.getValue().getPackage().getName();
                MapperBuilderAssistant assistant = new MapperBuilderAssistant(configuration, packageName);
                assistant.setCurrentNamespace(GeneralMapper.class.getName().concat(".").concat(type.getValue().getSimpleName()));
                generalSqlInjector.inject(configuration, assistant, GeneralMapper.class, type.getValue(), null);
            }
        }
    }

    public TableNameAnnotationProcessor(GeneralSqlInjector generalSqlInjector) {
        this.generalSqlInjector = generalSqlInjector;
    }
}
