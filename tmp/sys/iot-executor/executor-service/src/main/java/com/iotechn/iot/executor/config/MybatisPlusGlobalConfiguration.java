package com.iotechn.iot.executor.config;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-15
 * Time: 下午6:02
 */
@ConfigurationProperties(prefix = "mybatis-plus.configuration")
@Component
public class MybatisPlusGlobalConfiguration extends GlobalConfiguration {
}
