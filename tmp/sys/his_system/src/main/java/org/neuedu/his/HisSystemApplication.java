package org.neuedu.his;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HisSystemApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(HisSystemApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
