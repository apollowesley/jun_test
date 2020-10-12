package com.xieke.test.eurekaconsumerfeignhystrix;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "eureka-client", fallback = DcClientFallback.class)
public interface DcClient {

    @GetMapping("/dc")
    String consumer();

}