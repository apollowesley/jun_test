package cn.kiwipeach.design.other.masterworker;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Create Date: 2017/11/08
 * Description: 测试主函数
 * 更多参考：http://blog.csdn.net/lmdcszh/article/details/39698189
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class RunMain {

    /**
     * 场景：五个线程计算 1,2,3....100 每个数立方，最后求和,要求没计算一个数的立方的时候停顿100ms（模拟延时操作）
     */
    @Test
    public void testMasterWorker() {
        //固定使用5个Worker，并指定Worker
        Master m = new Master(new PlusWorker(), 10);
        //提交100个子任务
        for (int i = 0; i < 1000; i++) {
            m.submit(new BigDecimal(i));
        }
        //开始计算
        m.execute();
        BigDecimal re = new BigDecimal("0");
        //保存最终结算结果
        Map<String, BigDecimal> resultMap = m.getResultMap();

        //不需要等待所有Worker都执行完成，即可开始计算最终结果,
        while (resultMap.size() > 0 || !m.isComplete()) {
            //resultMap中有数据，并且worker还在工作，那么就把当前resultMap中的数据计算出来
            Set<String> keys = resultMap.keySet();
            for (String key : keys) {
                BigDecimal remove = resultMap.remove(key);
                re = re.add(remove);
            }
        }
        System.out.println("计算结果:" + re);
        //num:1..100        result:21790090      time:10s 627ms
    }

    @Test
    public void testFor() throws InterruptedException {
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < 1000; i++) {
            BigDecimal temp = new BigDecimal(i).multiply(new BigDecimal(i)).multiply(new BigDecimal(i));
            //假设每个计算需要耗时100ms
            Thread.sleep(100);
            sum = sum.add(temp);
        }
        System.out.println(sum);
        //1..100  reuslt:24502500 time:46s 919ms
    }


}
