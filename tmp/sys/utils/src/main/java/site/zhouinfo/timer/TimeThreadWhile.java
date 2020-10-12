package site.zhouinfo.timer;

/**
 * @author zhouinfo
 * @Create Date 2016-08-18 17:10
 */
public class TimeThreadWhile {

    public static void main(String[] args) {
        final long timer = 10000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("time out!");
                    try {
                        Thread.sleep(timer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
