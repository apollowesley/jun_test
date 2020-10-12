package cn.afterturn.dao.factory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import cn.afterturn.dao.annotation.IDaoResult;

/**
 * 请求代理扫描类
 * 
 * @author JueYue
 * @date 2014年11月15日 下午9:46:34
 */
public class DaoClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    private Logger logger = LoggerFactory.getLogger(DaoClassPathMapperScanner.class);

    public DaoClassPathMapperScanner(BeanDefinitionRegistry registry,
                                     Class<? extends Annotation> annotation) {
        super(registry, false);
        addIncludeFilter(new AnnotationTypeFilter(annotation));
        if (!IDaoResult.class.equals(annotation)) {
            addIncludeFilter(new AnnotationTypeFilter(IDaoResult.class));
        }
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No Lemur Dao mapper was found in '" + Arrays.toString(basePackages)
                        + "' package. Please check your configuration.");
        }
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getPropertyValues().add("proxy",
                getRegistry().getBeanDefinition("jdbcDaoProxyHandler"));
            definition.getPropertyValues().add("daoInterface", definition.getBeanClassName());
            definition.setBeanClass(DaoBeanFactory.class);
        }

        return beanDefinitions;
    }

    /**
     * 默认不允许接口的,这里重写,覆盖下
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface()
               && beanDefinition.getMetadata().isIndependent();
    }
}
