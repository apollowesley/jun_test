package com.turingoal.generator.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.turingoal.generator.commons.TgCodeGenerator;
import com.turingoal.generator.commons.TgSpringApplicationRunner;
import com.turingoal.generator.commons.TgSpringPropertiesSystem;

/**
 * 基础配置<br>
 * 注解SpringBootApplication等价于以默认属性使用Configuration，EnableAutoConfiguration和ComponentScan。SpringBoot推荐使用SpringBootConfiguration代替Configuration<br>
 * EnableAutoConfiguration：Spring应用上下文的自动化配置，尝试去猜测和配置你需要的bean。
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = { // 尽量精细，优化性
        "com.turingoal.generator.core.service" // core service
})
@EnableConfigurationProperties(TgSpringPropertiesSystem.class) // 自定义配置文件，多个用“，”分隔，其他地方注入后可以直接使用
public class TgSpringApplicationBaseConfig {
    /**
     * 代码生成服务
     */
    @Bean
    public TgCodeGenerator tgCodeGenerator() {
        return new TgCodeGenerator();
    }

    /**
     * 容器启动完成执行
     */
    @Bean
    public TgSpringApplicationRunner tgSpringApplicationRunner() {
        return new TgSpringApplicationRunner();
    }
}
