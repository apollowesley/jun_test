package cn.kiwipeach.design.other.masterworker;

import java.math.BigDecimal;

/**
 * Create Date: 2017/11/08
 * Description: 立方和Worker子类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class PlusWorker extends Worker {

    @Override
    public BigDecimal handle(BigDecimal input) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return input.multiply(input).multiply(input);
    }
}
