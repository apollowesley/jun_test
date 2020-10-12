package com.luoqy.speedy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.luoqy.speedy.common.FirstCreateBase;

@SpringBootApplication
@EnableCaching
@EnableScheduling//定时任务
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@MapperScan({"com.luoqy.speedy.core.base.dao","com.luoqy.speedy.modular.system.dao","com.luoqy.speedy.modular.web.dao"})
public class SpeedyApplication {
	public static void main(String[] args) {
		//FirstCreateBase.initBase();
		SpringApplication.run(SpeedyApplication.class, args);
		FirstCreateBase.unlock();
		System.err.println("SpeedyApplication is success!");
	}
	
	/**
     * 解决同一时间只能一个定时任务执行的问题
     * */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }
}
