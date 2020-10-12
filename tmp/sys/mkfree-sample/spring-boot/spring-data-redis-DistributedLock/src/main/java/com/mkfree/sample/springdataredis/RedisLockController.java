package com.mkfree.sample.springdataredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisLockController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisLock redisLock;


    @GetMapping("/test1")
    public String test1() throws InterruptedException {
        try {
            // 获取当前 key 分布式锁，获取成功可以继续往下执行
            redisLock.lock("DistributedLock");

            logger.info("具体的业务代码");
            // 运行业务代码模式时间，9秒
            Thread.sleep(90000);

            return "test1";
        } finally {
            // 解锁
            redisLock.unLock("DistributedLock");
        }
    }

    @GetMapping("/test2")
    public String test2() {
        try {
            redisLock.lock("DistributedLock");
            logger.info("test2222222");
            return "test2";
        } finally {
            redisLock.unLock("DistributedLock");
        }
    }
}
