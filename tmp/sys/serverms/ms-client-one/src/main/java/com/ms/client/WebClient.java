package com.ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhuxiaomeng
 * @date 2019-03-28.
 * @email 154040976@qq.com
 */
@FeignClient(name = "web")
public interface WebClient {

    @GetMapping("/web")
    public String re();
}
