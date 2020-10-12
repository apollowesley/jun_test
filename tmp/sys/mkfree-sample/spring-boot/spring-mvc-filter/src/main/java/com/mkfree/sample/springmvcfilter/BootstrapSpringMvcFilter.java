package com.mkfree.sample.springmvcfilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class BootstrapSpringMvcFilter {

    private static Logger log = LoggerFactory.getLogger(BootstrapSpringMvcFilter.class);

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(BootstrapSpringMvcFilter.class, args);
        String[] strings = configurableApplicationContext.getBeanDefinitionNames();
    }

}