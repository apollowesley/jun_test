package com.baomidou.crab.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * <p>
 * 系统配置相关参数
 * </p>
 *
 * @author jobob
 * @since 2018-11-13
 */
@Data
@Component
@ConfigurationProperties(prefix = "crab")
public class SystemProperties {

    /**
     * 线上环境
     */
    private boolean online = false;

}
