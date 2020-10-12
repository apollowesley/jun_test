package com.iotechn.iot.gw.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iotechn.iot.executor.api.ExecutionOpenService;
import com.iotechn.iot.gw.api.ApiManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 下午10:43
 */
@Configuration
public class HttpApiConfig {

    @Bean
    public ApiManager apiManager() {
        ApiManager apiManager = new ApiManager();
        return apiManager;
    }


}
