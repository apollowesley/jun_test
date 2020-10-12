package site.zhouinfo.utilities;

import site.zhouinfo.datetime.DateUtil;
import site.zhouinfo.file.FileWriteUtils;
import site.zhouinfo.file.PropertiesUtils;
import site.zhouinfo.network.GetLocalIPAddress;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ${DESCRIPTION}
 *
 * @author zhouinfo
 *         Date 2016-08-19 12:23
 */
public class TimeTimerTaskTest {

    /**
     * 使用 定时器 TimerTask 和 获取 ip138.com GetHtml 的网页 取得本机ip 提取出来 fileWritesUtils 保存到文本中
     *
     * @param args
     */
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String ip = "IP:" + GetLocalIPAddress.getMyIP() + "  " + DateUtil.now();
                System.out.println(ip);
                FileWriteUtils.fileWriterEndC(PropertiesUtils.getKeyValue("saveFile"), ip + "\r\n");
            }
        };
        Timer timer = new Timer();
        long delay = 1;
        long intevalPeriod = Long.parseLong(PropertiesUtils.getKeyValue("timer")); //一小时
        // schedules the task to be run in an interval
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }
}
