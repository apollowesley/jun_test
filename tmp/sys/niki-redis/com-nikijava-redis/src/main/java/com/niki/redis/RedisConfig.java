package com.niki.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com-public
 * @Package: com.config
 * @ClassName: RedisConfig
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/2/26 14:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Component
@Data
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;

    private int maxIdle = 8;

    private long maxWaitMillis = -1;


}