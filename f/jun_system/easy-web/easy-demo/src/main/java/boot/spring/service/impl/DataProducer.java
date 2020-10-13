package boot.spring.service.impl;

import boot.spring.po.TestVo;

import java.util.concurrent.BlockingQueue;

public class DataProducer implements Runnable {
    private BlockingQueue<TestVo> queue;
    private final TestVo testVo;

    public DataProducer(BlockingQueue<TestVo> queue, TestVo testVo) {
        this.queue = queue;
        this.testVo = testVo;
    }

    public void run() {
        try {
            generateData();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void generateData() throws InterruptedException {
        //TODO:这里处理数据验证、检查等过程
        //System.out.println("生产" + testVo.getId() + "完成!");
        queue.put(testVo);
    }
}
