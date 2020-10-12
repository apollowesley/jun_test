package com.flypigs.resubmit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

@Configuration
public class ResubmitConfig implements ImportAware {

    private AnnotationAttributes enableResubmit;

    /**
     * 防重复提交 aop
     * @return
     */
    @Bean
    ResubmitAspect resubmitAspect(){
        return new ResubmitAspect();
    }

    @Bean
    ResubmitResolvers resubmitResolvers(@Autowired(required = false) List<ResubmitResolver> resubmitResolvers) {
        ResubmitResolvers resolvers = new ResubmitResolvers();
        resolvers.addResubmitResolver(new ExceptionResolver());
        resolvers.addResubmitResolver(new PrintResolver());
        resolvers.addResubmitResolver(new HttpStatusResolver());
        resolvers.addResubmitResolvers(resubmitResolvers);
        resolvers.setDefaultResubmitResolver(BeanUtils.instantiateClass(enableResubmit.getClass("defaultResolvers")));
        return resolvers;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        enableResubmit = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnableResubmit.class.getName(), false));
    }

}
