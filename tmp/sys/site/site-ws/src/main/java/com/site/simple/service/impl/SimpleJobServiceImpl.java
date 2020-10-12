package com.site.simple.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.site.simple.service.SimpleJobService;

/**
 * 
 * @Description  定时任务示例service
 * @author 王鑫 
 * @date Nov 9, 2016 10:08:14 AM
 */
@Service("simpleJobService")
public class SimpleJobServiceImpl implements SimpleJobService {

    private static final Logger log = LoggerFactory.getLogger(SimpleJobServiceImpl.class);

    @Override
    public void execute() {

        log.info("# job console ....");

    }

}
