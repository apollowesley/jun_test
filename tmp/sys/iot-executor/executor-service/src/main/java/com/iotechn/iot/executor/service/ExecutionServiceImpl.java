package com.iotechn.iot.executor.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.iotechn.iot.executor.api.ExecutionService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 下午5:14
 */
//@Service
public class ExecutionServiceImpl implements ExecutionService {
    @Override
    public String addClass(String content, Long userId) {
        return null;
    }

    @Override
    public String reloadClass(String content, String secret, Long userId) {
        return null;
    }
}
