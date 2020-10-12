package com.turingoal.generator;

import org.springframework.boot.SpringApplication;
import com.turingoal.generator.config.TgSpringApplicationBaseConfig;

/**
 * 启动入口
 */
public final class TgBootApplication {
    private TgBootApplication() {
        throw new Error("不能实例化");
    }

    /**
     * jar形式。调用TgSpringApplicationBaseConfig启动
     */
    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(TgSpringApplicationBaseConfig.class);
        app.run(args);
    }
}
