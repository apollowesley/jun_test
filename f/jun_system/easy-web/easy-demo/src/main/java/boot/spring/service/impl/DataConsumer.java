package boot.spring.service.impl;

import boot.spring.po.TestVo;
import boot.spring.service.TestService;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DataConsumer implements Runnable {

    private BlockingQueue<TestVo> queue;
    private final TestService testService;

    public DataConsumer(BlockingQueue<TestVo> queue, TestService testService) {
        this.queue = queue;
        this.testService = testService;
    }

    public void run() {
        try {
            List<TestVo> list = Lists.newArrayList();
            while (true) {
                TestVo testVo = queue.take();
                list.add(testVo);
                if (testVo.getId() == -1) {
                    list.remove(testVo);
                    if (list.size() > 0) {
                        testService.batchAdd(list);
                    }
                    System.out.println("消费全部结束了");
                    //return;
                } else if (list.size() == 20000) {
                    testService.batchAdd(list);
                    System.out.println("批量入库了" + list.size());
                    list.clear();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
