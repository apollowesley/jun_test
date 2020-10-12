package com.handy.task;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/6 11:09
 */
public class MsgDelTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(MsgDelTask.class);

    @Override
    public void run() {
        logger.info("2号任务,线程为:" + Thread.currentThread().getName() + "执行时间为:" + DateUtil.now());
    }
}
