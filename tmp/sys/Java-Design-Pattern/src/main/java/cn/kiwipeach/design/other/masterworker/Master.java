package cn.kiwipeach.design.other.masterworker;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Create Date: 2017/11/07
 * Description: Master类型:任务的分配和最终结果的合成
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class Master {
    /**
     * 任务队列
     */
    protected Queue<BigDecimal> workQueue = new ConcurrentLinkedQueue<BigDecimal>();
    /**
     * Worker进程队列
     */
    protected Map<String, Thread> threadMap = new HashMap<String, Thread>();
    /**
     * 子任务处理结果集
     */
    protected Map<String, BigDecimal> resultMap = new ConcurrentHashMap<String, BigDecimal>();

    /**
     * 判断子任务都结束了
     *
     * @return 返回判断结果
     */
    public boolean isComplete() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if (entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    //Master的构造，需要一个Worker进程逻辑，和需要Worker进程数量
    public Master(Worker worker, int countWorker) {
        worker.setWorkQueue(workQueue);
        worker.setResultMap(resultMap);
        for (int i = 0; i < countWorker; i++) {
            threadMap.put(Integer.toString(i), new Thread(worker, Integer.toString(i)));
        }
    }

    //提交一个任务
    public void submit(BigDecimal job) {
        workQueue.add(job);
    }

    //返回子任务结果集
    public Map<String, BigDecimal> getResultMap() {
        return resultMap;
    }


    //开始运行所有的Worker进程，进行处理
    public void execute() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            entry.getValue().start();
        }
    }
}