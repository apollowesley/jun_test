package com.handy.task;

import cn.hutool.core.date.DateUtil;
import com.handy.common.util.ApplicationContextUtil;
import com.handy.service.entity.msg.MsgMessage;
import com.handy.service.service.msg.IMsgMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/9/6 11:07
 */
public class MsgTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(MsgTask.class);

    @Override
    public void run() {
        IMsgMessageService msgMessageService = (IMsgMessageService) ApplicationContextUtil.getBean("msgMessageService");
        List<MsgMessage> list = msgMessageService.list();
        logger.info("1号任务,线程为:" + Thread.currentThread().getName() + "执行时间为:" + DateUtil.now() + "查询出来消息总条数为:" + list.size());
    }

}
