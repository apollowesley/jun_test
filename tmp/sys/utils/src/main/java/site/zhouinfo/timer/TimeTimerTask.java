package site.zhouinfo.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhouinfo
 * @Create Date 2016-08-18 17:18
 */
public class TimeTimerTask {

    public static void main(String[] args) {

    }

    public static void TiemTimerTast() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("time out!");
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 1000;
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }
}
