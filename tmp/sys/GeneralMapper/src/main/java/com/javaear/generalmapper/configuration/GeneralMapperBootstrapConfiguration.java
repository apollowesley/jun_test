package com.javaear.generalmapper.configuration;

import com.javaear.generalmapper.plugin.GeneralSqlInjector;
import com.javaear.generalmapper.processor.TableNameAnnotationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralMapperBootstrapConfiguration {

    @Bean
    public TableNameAnnotationProcessor generalMapper() {
        return new TableNameAnnotationProcessor(generalSqlInjector());
    }

    @Bean
    public GeneralSqlInjector generalSqlInjector() {
        return new GeneralSqlInjector();
    }

}
