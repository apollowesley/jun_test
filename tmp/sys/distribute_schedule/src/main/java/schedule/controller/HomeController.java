package schedule.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhm on 16-9-2.
 */
@RestController
public class HomeController {
    @Scheduled(cron="0/5 * * * * *")
    public void aaa(){
        System.out.println(11111);
    }
    public void bbb(){
        System.out.println(22222);
    }
}
