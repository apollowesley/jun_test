package com.tianyalei.test;


import com.tianyalei.async.callback.ICallback;
import com.tianyalei.async.callback.IWorker;
import com.tianyalei.async.executor.timer.SystemClock;
import com.tianyalei.async.worker.WorkResult;

/**
 * @author wuweifeng wrote on 2019-11-20.
 */
public class MyWorker1 implements IWorker<String, String>, ICallback<String, String> {
    @Override
    public String action(String object) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result = " + SystemClock.now() + "---param = " + object + " from 1";
    }


    @Override
    public String defaultValue() {
        return "worker1--default";
    }

    @Override
    public void begin() {
        //System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void result(boolean success, String param, WorkResult<String> workResult) {
        if (success) {
            System.out.println("callback worker1 success--" + SystemClock.now() + "----" + workResult.getResult());
        } else {
            System.err.println("callback worker1 failure--" + SystemClock.now() + "----" + workResult.getResult());
        }
    }

}