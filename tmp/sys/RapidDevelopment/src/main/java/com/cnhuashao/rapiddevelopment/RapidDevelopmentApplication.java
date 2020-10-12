package com.cnhuashao.rapiddevelopment;

import com.cnhuashao.rapiddevelopment.core.demo4.config.XssConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        XssConfig.class,
})
@SpringBootApplication
public class RapidDevelopmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RapidDevelopmentApplication.class, args);
    }

}
