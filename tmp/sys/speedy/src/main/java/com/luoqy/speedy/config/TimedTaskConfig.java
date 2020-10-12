package com.luoqy.speedy.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class TimedTaskConfig {
    /**
     * 更新有最新章节的小说
     */
    @Scheduled(cron="0 0 */8 * * ?")
    public void updateNewFiction(){
    	
    }
    /**
     * 5分钟全量更新未进行全量同步的小说内容
     */
    @Scheduled(fixedDelayString = "600000") //更新时间
    public void update(){
    }
    /**
     * 全量更新存储基础信息
     * cron="* * *\\/120 * * ?"
     * */
    @Scheduled(fixedDelayString="450000000")
    private void saveBaseFiction(){
    	
    }
}
