package cn.kiwipeach.design.other.masterworker;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Queue;

/**
 * Create Date: 2017/11/07
 * Description: Worder类:用于实际处理一个任务
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class Worker implements Runnable{
    /**
     * 任务队列，用于取得子任务
     */
    protected Queue<BigDecimal> workQueue;
    /**
     * 子任务处理结果集
     */
    protected Map<String ,BigDecimal> resultMap;

    /**
     * 设置任务队列
     * @param workQueue
     */
    public void setWorkQueue(Queue<BigDecimal> workQueue){
        this.workQueue= workQueue;
    }

    /**
     * 设置返回结果
     * @param resultMap 返回结果
     */
    public void setResultMap(Map<String ,BigDecimal> resultMap){
        this.resultMap=resultMap;
    }

    /**
     * 子任务处理的逻辑，在子类中实现具体逻辑
     * @param input 处理对象
     * @return 返回结果
     */
    public BigDecimal handle(BigDecimal input){
        return input;
    }

    public void run() {
        while(true){
            //获取子任务
            BigDecimal input= workQueue.poll();
            if(input==null){
                break;
            }
            //处理子任务
            BigDecimal re = handle(input);
            resultMap.put(Integer.toString(input.hashCode()), re);
        }
    }
}
