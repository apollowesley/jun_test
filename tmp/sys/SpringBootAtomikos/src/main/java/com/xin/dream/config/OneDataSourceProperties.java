package com.xin.dream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.one")
public class OneDataSourceProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
