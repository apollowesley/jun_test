package com.flypigs.lock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

    @Bean
    LockManager lockManager(){
        return new LockManager();
    }
}
